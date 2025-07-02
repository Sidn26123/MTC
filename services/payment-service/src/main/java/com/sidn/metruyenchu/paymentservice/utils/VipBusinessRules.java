package com.sidn.metruyenchu.paymentservice.utils;

import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.entity.VipMembership;
import com.sidn.metruyenchu.paymentservice.entity.VipPlan;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VipBusinessRules {

    /**
     * Check if user can upgrade/downgrade VIP plan
     */
    public boolean canChangePlan(VipMembership currentMembership, VipPlan newPlan) {
        if (currentMembership == null) {
            return true; // Can purchase any plan
        }
        
        // Can always upgrade to higher tier
        // Can downgrade only if current plan is expiring soon (within 7 days)
        LocalDateTime sevenDaysFromNow = LocalDateTime.now().plusDays(7);
        return currentMembership.getEndDate().isBefore(sevenDaysFromNow);
    }

    /**
     * Calculate prorated refund for plan changes
     */
    public BigDecimal calculateProratedRefund(VipMembership membership) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(membership.getEndDate())) {
            return BigDecimal.ZERO;
        }
        
        long totalDays = Duration.between(membership.getStartDate(), membership.getEndDate()).toDays();
        long remainingDays = Duration.between(now, membership.getEndDate()).toDays();
        
        BigDecimal dailyRate = membership.getPlan().getPrice()
            .divide(BigDecimal.valueOf(totalDays), 2, RoundingMode.HALF_UP);
        
        return dailyRate.multiply(BigDecimal.valueOf(remainingDays));
    }

    /**
     * Get recommended coupons for user
     */
    public List<Coupon> getRecommendedCoupons(String userId, CouponApplicableType applicableType) {
        // Logic to recommend best coupons based on:
        // - User's purchase history
        // - Coupon value and restrictions
        // - Expiry dates
        // - User's collected but unused coupons
        return Collections.emptyList(); // Implement based on business rules
    }
}