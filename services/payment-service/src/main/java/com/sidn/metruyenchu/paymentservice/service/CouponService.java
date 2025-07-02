package com.sidn.metruyenchu.paymentservice.service;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponValidationResult;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.UserCouponResponse;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.entity.UserCoupon;
import com.sidn.metruyenchu.paymentservice.mapper.CouponMapper;
import com.sidn.metruyenchu.paymentservice.mapper.UserCouponMapper;
import com.sidn.metruyenchu.paymentservice.repository.CouponRepository;
import com.sidn.metruyenchu.paymentservice.repository.CouponUsageRepository;
import com.sidn.metruyenchu.paymentservice.repository.UserCouponRepository;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.UserCouponStatus;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponUsageRepository couponUsageRepository;
    private final UserCouponMapper userCouponMapper;
    private final CouponUsageService couponUsageService;

    /**
     * Collect coupon for user (from events, holidays, etc.)
     */
    public UserCouponResponse collectCoupon(String userId, String couponCode, String source) {
        Coupon coupon = couponRepository.findByCodeAndStatus(couponCode, CouponStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        // Check if user already has this coupon
        Optional<UserCoupon> existingCoupon = userCouponRepository.findByUserIdAndCouponId(userId, coupon.getId());
        if (existingCoupon.isPresent()) {
            throw new AppException(ErrorCode.COUPON_ALREADY_EXISTS);
        }

        // Check coupon validity
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getValidFrom()) || now.isAfter(coupon.getValidUntil())) {
            throw new AppException(ErrorCode.COUPON_IS_NOT_VALID);
        }

        UserCoupon userCoupon = UserCoupon.builder()
                .userId(userId)
                .coupon(coupon)
                .obtainedFrom(source)
                .build();

        userCouponRepository.save(userCoupon);

        return userCouponMapper.toResponse(userCoupon);
    }

    /**
     * Get user's available coupons
     */
    public List<UserCouponResponse> getUserCoupons(String userId, CouponApplicableType applicableType) {
        LocalDateTime now = LocalDateTime.now();

        List<UserCoupon> userCoupons = userCouponRepository
                .findByUserIdAndStatusAndCouponValidUntilAfter(userId, UserCouponStatus.AVAILABLE, now);

        return userCoupons.stream()
                .filter(uc -> applicableType == null ||
                        uc.getCoupon().getApplicableType() == CouponApplicableType.ALL ||
                        uc.getCoupon().getApplicableType() == applicableType)
                .map(userCouponMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Validate coupon and calculate discount
     */
    public CouponValidationResult validateAndCalculateDiscount(String userId, String couponCode,
                                                               BigDecimal purchaseAmount, CouponApplicableType applicableType) {

        // Find user's coupon
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponCode(userId, couponCode)
                .orElse(null);

        if (userCoupon == null) {
            return CouponValidationResult.invalid("Coupon not found or not owned by user");
        }

        Coupon coupon = userCoupon.getCoupon();

        // Validate coupon
        LocalDateTime now = LocalDateTime.now();
        if (coupon.getStatus() != CouponStatus.ACTIVE) {
            return CouponValidationResult.invalid("Coupon is inactive");
        }

        if (now.isBefore(coupon.getValidFrom()) || now.isAfter(coupon.getValidUntil())) {
            return CouponValidationResult.invalid("Coupon is expired or not yet valid");
        }

        if (userCoupon.getStatus() != UserCouponStatus.AVAILABLE) {
            return CouponValidationResult.invalid("Coupon is not available");
        }

        if (userCoupon.getUsageCount() >= coupon.getUserUsageLimit()) {
            return CouponValidationResult.invalid("Coupon usage limit exceeded");
        }

        if (coupon.getApplicableType() != CouponApplicableType.ALL &&
                coupon.getApplicableType() != applicableType) {
            return CouponValidationResult.invalid("Coupon not applicable for this purchase type");
        }

        if (coupon.getMinPurchaseAmount() != null &&
                purchaseAmount.compareTo(coupon.getMinPurchaseAmount()) < 0) {
            return CouponValidationResult.invalid("Purchase amount below minimum required");
        }

        // Calculate discount
        BigDecimal discountAmount = calDiscountedAmount(coupon, purchaseAmount);
//        if (coupon.getType() == CouponType.PERCENTAGE) {
//            discountAmount = purchaseAmount.multiply(coupon.getValue()).divide(BigDecimal.valueOf(100));
//        } else {
//            discountAmount = coupon.getValue();
//        }
//
//        // Apply max discount limit
//        if (coupon.getMaxDiscountAmount() != null &&
//                discountAmount.compareTo(coupon.getMaxDiscountAmount()) > 0) {
//            discountAmount = coupon.getMaxDiscountAmount();
//        }
//
//        // Cannot exceed purchase amount
//        if (discountAmount.compareTo(purchaseAmount) > 0) {
//            discountAmount = purchaseAmount;
//        }

        return CouponValidationResult.valid(coupon, discountAmount);
    }

    /**
     * Use coupon (called after successful payment)
     */
    public void useCoupon(String userId, String couponId, String transactionId) {
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponId(userId, couponId)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        Coupon coupon = userCoupon.getCoupon();

        // Update usage counts
        userCoupon.setUsageCount(userCoupon.getUsageCount() + 1);
        if (userCoupon.getUsageCount() >= coupon.getUserUsageLimit()) {
            userCoupon.setStatus(UserCouponStatus.USED_UP);
        }

        if (userCoupon.getFirstUsedAt() == null) {
            userCoupon.setFirstUsedAt(LocalDateTime.now());
        }
        userCoupon.setLastUsedAt(LocalDateTime.now());

        coupon.setUsageCount(coupon.getUsageCount() + 1);

        userCouponRepository.save(userCoupon);
        couponRepository.save(coupon);
    }

    public Coupon getCouponByCodeAndStillValid(String couponCode) {
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();
        if (coupon.getStatus() != CouponStatus.ACTIVE ||
            now.isBefore(coupon.getValidFrom()) ||
            now.isAfter(coupon.getValidUntil())) {
            throw new AppException(ErrorCode.COUPON_IS_NOT_VALID);
        }
        if (couponUsageService.isCouponCanUsage(coupon) ) {
            throw new AppException(ErrorCode.COUPON_USAGE_LIMIT_EXCEEDED);
        }
        return coupon;
    }

    public BigDecimal calDiscountedAmount(Coupon coupon, BigDecimal purchaseAmount) {
        BigDecimal discountedAmount = BigDecimal.ZERO;

        if (coupon.getMinPurchaseAmount() != null && purchaseAmount.compareTo(coupon.getMinPurchaseAmount()) < 0) {
            return purchaseAmount; // Not eligible for discount
        }


        if (coupon.getType() == CouponType.PERCENTAGE) {
            discountedAmount = purchaseAmount.multiply(coupon.getValue()).divide(BigDecimal.valueOf(100));
        } else {
            discountedAmount = coupon.getValue();
        }

        if (coupon.getMaxDiscountAmount() != null &&
            discountedAmount.compareTo(coupon.getMaxDiscountAmount()) > 0) {
            discountedAmount = coupon.getMaxDiscountAmount();
        }

        return discountedAmount;
    }
}