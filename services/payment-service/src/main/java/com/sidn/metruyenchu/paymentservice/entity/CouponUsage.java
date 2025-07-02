package com.sidn.metruyenchu.paymentservice.entity;
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
@Table(name = "coupon_usages")
public class CouponUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "user_id", nullable = false)
    String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    Coupon coupon;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    Transactions transaction;

    @Column(name = "original_amount", nullable = false, precision = 19, scale = 2)
    BigDecimal originalAmount;

    @Column(name = "discount_amount", nullable = false, precision = 19, scale = 2)
    BigDecimal discountAmount;

    @Column(name = "final_amount", nullable = false, precision = 19, scale = 2)
    BigDecimal finalAmount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime usedAt;
}