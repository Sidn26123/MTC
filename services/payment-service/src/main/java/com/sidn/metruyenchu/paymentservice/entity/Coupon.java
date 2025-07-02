package com.sidn.metruyenchu.paymentservice.entity;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.VipStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, unique = true)
    String code;

    @Column(nullable = false)
    String name;

    @Column(length = 1000)
    String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CouponType type; // PERCENTAGE, FIXED_AMOUNT

    @Column(nullable = false, precision = 19, scale = 2)
    BigDecimal value; // Percentage or fixed amount

    @Column(name = "min_purchase_amount", precision = 19, scale = 2)
    BigDecimal minPurchaseAmount;

    @Column(name = "max_discount_amount", precision = 19, scale = 2)
    BigDecimal maxDiscountAmount;

    @Column(name = "usage_limit")
    Integer usageLimit; // null = unlimited

    @Column(name = "usage_count", nullable = false)
    @Builder.Default
    Integer usageCount = 0;

    @Column(name = "user_usage_limit")
    @Builder.Default
    Integer userUsageLimit = 1; // Per user limit

    @Column(name = "valid_from", nullable = false)
    LocalDateTime validFrom;

    @Column(name = "valid_until", nullable = false)
    LocalDateTime validUntil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    CouponStatus status = CouponStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CouponApplicableType applicableType; // VIP_MEMBERSHIP, CONTENT_PURCHASE, ALL

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "coupon")
    List<UserCoupon> userCoupons;

    @OneToMany(mappedBy = "coupon")
    List<CouponUsage> usages;
}