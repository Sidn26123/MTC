package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.ContentPurchaseRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionsCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponValidationResult;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipPurchaseRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipStatusResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.VipMembershipResponse;
import com.sidn.metruyenchu.paymentservice.entity.*;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionType;
import com.sidn.metruyenchu.paymentservice.mapper.TransactionsMapper;
import com.sidn.metruyenchu.paymentservice.mapper.VipMembershipMapper;
import com.sidn.metruyenchu.paymentservice.repository.CurrencyRepository;
import com.sidn.metruyenchu.paymentservice.repository.VipChapterUsageRepository;
import com.sidn.metruyenchu.paymentservice.repository.VipMembershipRepository;
import com.sidn.metruyenchu.paymentservice.repository.VipPlanRepository;
import com.sidn.metruyenchu.shared_library.enums.payment.ContentType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.VipStatus;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VipMembershipService {

    VipMembershipRepository membershipRepository;
    VipPlanRepository planRepository;
    WalletService walletService;
    TransactionsService transactionService;
    TransactionsMapper transactionsMapper;
    CouponService couponService;
    CurrencyRepository currencyRepository;
    CurrencyService currencyService;
    VipChapterUsageRepository vipChapterUsageRepository;
    VipMembershipMapper vipMembershipMapper;
    ContentPurchaseService contentPurchaseService;
    VipAuditService vipAuditService;

    /**
     * Purchase VIP membership
     */
    @Transactional
    public VipMembershipResponse purchaseVipMembership(String userId,
                                                       VipPurchaseRequest request) {
        
        // 1. Validate plan
        VipPlan plan = planRepository.findByIdAndActiveTrue(request.getPlanId())
            .orElseThrow(() -> new AppException(ErrorCode.VIP_PLAN_NOT_FOUND));
        
        // 2. Calculate final price with coupon
        BigDecimal finalPrice = plan.getPrice();
        Coupon appliedCoupon = couponService.getCouponByCodeAndStillValid(request.getCouponCode());

        BigDecimal discountAmount = BigDecimal.ZERO;
        
        if (request.getCouponCode() != null) {
            CouponValidationResult validation = couponService.validateAndCalculateDiscount(
                userId, request.getCouponCode(), finalPrice, CouponApplicableType.VIP_MEMBERSHIP);
            
            if (validation.getValid()) {
                appliedCoupon = validation.getCoupon();
                discountAmount = validation.getDiscountAmount();
                finalPrice = finalPrice.subtract(discountAmount);
            }
        }
        //Chỉ duy nhất wallet XU moới có thể payment
        Currency currency = currencyRepository.findByCode("XU").orElseThrow();

        // 3. Check wallet balance
        if (!walletService.hasSufficientBalance(userId,currency.getId(), finalPrice)) {
            throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        //Check whether user has an active membership
        VipMembership existingMembership = membershipRepository.findByUserIdAndStatusAndEndDateAfter(
            userId, VipStatus.ACTIVE, LocalDateTime.now()).orElse(null);

        if (existingMembership != null) {
            throw new AppException(ErrorCode.VIP_MEMBERSHIP_ALREADY_ACTIVE);
        }


        // 4. Deduct from wallet
        Transactions transaction = Transactions.builder()

                .build();
        
        // 5. Create VIP membership
        VipMembership membership = createVipMembership(userId, plan, transaction);
        

        if (appliedCoupon != null) {
            couponService.useCoupon(userId, appliedCoupon.getId(), transaction.getId());
        }
        vipAuditService.logVipPurchase(userId, membership.getId(), plan.getId(), finalPrice);
        return vipMembershipMapper.toResponse(membership);
    }

    /**
     * Check if user can read VIP chapter
     */
    public boolean canReadVipChapter(String userId, String chapterId) {
        VipMembership activeMembership = getActiveMembership(userId);
        if (activeMembership == null) {
            return false;
        }
        
        // Check if user has remaining free chapters this month
        String currentMonth = YearMonth.now().toString();
        LocalDateTime lastReset = activeMembership.getLastChapterReset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        if (lastReset == null || !currentMonth.equals(lastReset.format(formatter))) {
            resetMonthlyChapterUsage(activeMembership);
        }
//        if (!currentMonth.equals(activeMembership.getLastChapterReset()?.format(DateTimeFormatter.ofPattern("yyyy-MM")))) {
//            resetMonthlyChapterUsage(activeMembership);
//        }
        
        return activeMembership.getChaptersUsedThisMonth() < activeMembership.getPlan().getFreeChaptersPerMonth();
    }

    /**
     * Record chapter read
     */
    public void recordChapterRead(String userId, String novelId, String chapterId) {
        VipMembership membership = getActiveMembership(userId);
        if (membership == null) {
            throw new AppException(ErrorCode.VIP_MEMBERSHIP_NOT_FOUND);
        }
        //Nếu người dùng đã mua chương này thì không cần ghi lại việc đọc chương
        if (contentPurchaseService.hasPurchasedContent(userId, chapterId, ContentType.CHAPTER)){
            log.info("User {} has already purchased chapter {}, no need to record read.", userId, chapterId);
            return;
        }
        Currency currency = currencyService.getDefaultPurchaseCurrency();
        //Add purchase content
        contentPurchaseService.purchaseContent(
                ContentPurchaseRequest.builder()
                        .itemType(ContentType.CHAPTER)
                        .itemId(chapterId)
                        .userId(userId)
                        .currencyId(currency.getId())
                        .transactionType(TransactionType.VIP_BENEFIT)
                        .price(BigDecimal.valueOf(0))
                        .quantity(1)
                        .build()
        );


        // Update usage count
        membership.setChaptersUsedThisMonth(membership.getChaptersUsedThisMonth() + 1);
        membershipRepository.save(membership);
        
        // Record detailed usage
        VipChapterUsage usage = VipChapterUsage.builder()
            .membership(membership)
            .userId(userId)
            .storyId(novelId)
            .chapterId(chapterId)
            .yearMonth(YearMonth.now().toString())
            .build();

        vipChapterUsageRepository.save(usage);

        vipAuditService.logChapterRead( userId, chapterId, membership.getId());
    }

    /**
     * Get user's VIP status and remaining chapters
     */
    public VipStatusResponse getVipStatus(String userId) {
        VipMembership membership = getActiveMembership(userId);
        
        if (membership == null) {
            return VipStatusResponse.builder()
                .isVip(false)
                .build();
        }
        
        String currentMonth = YearMonth.now().toString();
        LocalDateTime lastReset = membership.getLastChapterReset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        if (lastReset == null || !currentMonth.equals(lastReset.format(formatter))) {
            resetMonthlyChapterUsage(membership);
        }
//        if (!currentMonth.equals(membership.getLastChapterReset()?.format(DateTimeFormatter.ofPattern("yyyy-MM")))) {
//            resetMonthlyChapterUsage(membership);
//        }
        
        int remainingChapters = membership.getPlan().getFreeChaptersPerMonth() - membership.getChaptersUsedThisMonth();
        
        return VipStatusResponse.builder()
            .isVip(true)
            .planName(membership.getPlan().getDisplayName())
            .expiresAt(membership.getEndDate())
            .totalChaptersPerMonth(membership.getPlan().getFreeChaptersPerMonth())
            .usedChaptersThisMonth(membership.getChaptersUsedThisMonth())
            .remainingChaptersThisMonth(Math.max(0, remainingChapters))
            .autoRenewal(membership.getAutoRenewal())
            .build();
    }

    private VipMembership getActiveMembership(String userId) {
        return membershipRepository.findByUserIdAndStatusAndEndDateAfter(
            userId, VipStatus.ACTIVE, LocalDateTime.now()).orElseThrow(() ->
                new AppException(ErrorCode.VIP_MEMBERSHIP_NOT_FOUND));
    }

    private VipMembership createVipMembership(String userId, VipPlan plan, Transactions transaction) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(plan.getDurationDays());
        
        VipMembership membership = VipMembership.builder()
            .userId(userId)
            .plan(plan)
            .startDate(now)
            .endDate(endDate)
            .status(VipStatus.ACTIVE)
            .purchaseTransaction(transaction)
            .lastChapterReset(now)
            .build();
        
        return membershipRepository.save(membership);
    }

    private void resetMonthlyChapterUsage(VipMembership membership) {
        membership.setChaptersUsedThisMonth(0);
        membership.setLastChapterReset(LocalDateTime.now());
        membershipRepository.save(membership);
    }
}
