package com.sidn.metruyenchu.paymentservice.entity;

import com.sidn.metruyenchu.shared_library.enums.payment.ContentType;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho việc mua nội dung trong hệ thống, như chương truyện hoặc toàn bộ truyện.
 * <p>
 * Gắn với một giao dịch tài chính (`Transactions`). Lưu trữ thông tin về loại nội dung (chương, truyện),
 * mã định danh nội dung, số lượng, giá, chiết khấu, coupon được áp dụng và các thông tin liên quan khác.
 * <p>
 * Hỗ trợ mở rộng để xử lý nhiều loại nội dung khác nhau trong tương lai.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ContentPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    Transactions transaction;

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.STRING)
    ContentType itemType;

    @Column(name = "item_id", nullable = false)
    String itemId;

    @Column(nullable = false, precision = 19, scale = 2)
    BigDecimal price;

    @Column(name = "final_price", precision = 19, scale = 2)
    BigDecimal finalPrice;

    @Column(name = "currency_id", nullable = false)
    String currencyId;

    @Column(nullable = false)
    @Builder.Default
    Integer quantity = 1;

    @Column(precision = 19, scale = 2)
    BigDecimal discount;

    // NEW: Coupon information
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    Coupon appliedCoupon;

    @Column(name = "coupon_discount_amount", precision = 19, scale = 2)
    BigDecimal couponDiscountAmount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;
}
