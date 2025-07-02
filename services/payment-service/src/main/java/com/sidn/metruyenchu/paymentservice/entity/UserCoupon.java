package com.sidn.metruyenchu.paymentservice.entity;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.UserCouponStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.VipStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "user_coupons", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "coupon_id"}))
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "user_id", nullable = false)
    String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    Coupon coupon;

    @Column(name = "usage_count", nullable = false)
    @Builder.Default
    Integer usageCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    UserCouponStatus status = UserCouponStatus.AVAILABLE;

    @Column(name = "obtained_from")
    String obtainedFrom; // EVENT, HOLIDAY, PROMOTION, etc.

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime obtainedAt;

    @Column(name = "first_used_at")
    LocalDateTime firstUsedAt;

    @Column(name = "last_used_at")
    LocalDateTime lastUsedAt;
}
