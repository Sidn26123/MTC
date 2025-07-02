package com.sidn.metruyenchu.paymentservice.components;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.entity.UserCoupon;
import com.sidn.metruyenchu.paymentservice.entity.VipMembership;
import com.sidn.metruyenchu.paymentservice.repository.CouponRepository;
import com.sidn.metruyenchu.paymentservice.repository.UserCouponRepository;
import com.sidn.metruyenchu.paymentservice.repository.VipMembershipRepository;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.UserCouponStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.VipStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class VipScheduledTasks {

    private final VipMembershipRepository membershipRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    /**
     * Daily task to expire VIP memberships
     */
    @Scheduled(cron = "0 0 1 * * ?") // Run at 1 AM daily
    public void expireVipMemberships() {
        log.info("Starting VIP membership expiration task");
        
        LocalDateTime now = LocalDateTime.now();
        List<VipMembership> expiredMemberships = membershipRepository
            .findByExpiredAtBeforeAndStatus(now, VipStatus.ACTIVE);
        
        for (VipMembership membership : expiredMemberships) {
            membership.setStatus(VipStatus.EXPIRED);
            membershipRepository.save(membership);
            log.info("Expired VIP membership: {} for user: {}", 
                    membership.getId(), membership.getUserId());
        }
        
        log.info("Completed VIP membership expiration task. Expired {} memberships", 
                expiredMemberships.size());
    }

    /**
     * Daily task to expire coupons
     */
    @Scheduled(cron = "0 0 2 * * ?") // Run at 2 AM daily
    public void expireCoupons() {
        log.info("Starting coupon expiration task");
        
        LocalDateTime now = LocalDateTime.now();
        List<Coupon> expiredCoupons = couponRepository
            .findExpiredCoupons(now, CouponStatus.ACTIVE);
        
        for (Coupon coupon : expiredCoupons) {
            coupon.setStatus(CouponStatus.EXPIRED);
            couponRepository.save(coupon);
            
            // Update user coupons
            List<UserCoupon> userCoupons = userCouponRepository
                .findByUserIdAndStatusAndCouponValidUntilAfter(
                    null, UserCouponStatus.AVAILABLE, now);
            
            userCoupons.stream()
                .filter(uc -> uc.getCoupon().getId().equals(coupon.getId()))
                .forEach(uc -> {
                    uc.setStatus(UserCouponStatus.EXPIRED);
                    userCouponRepository.save(uc);
                });
        }
        
        log.info("Completed coupon expiration task. Expired {} coupons", expiredCoupons.size());
    }

    /**
     * Monthly task to reset VIP chapter usage
     */
    @Scheduled(cron = "0 0 0 1 * ?") // Run at midnight on 1st of each month
    public void resetMonthlyChapterUsage() {
        log.info("Starting monthly VIP chapter usage reset");
        
        List<VipMembership> activeMembers = membershipRepository
            .findByStatusAndEndDateAfter(VipStatus.ACTIVE, LocalDateTime.now());
        for (VipMembership membership : activeMembers) {
            membership.setChaptersUsedThisMonth(0);
            membership.setLastChapterReset(LocalDateTime.now());
            membershipRepository.save(membership);
        }

        log.info("Completed monthly chapter usage reset for {} members", activeMembers.size());
    }
}