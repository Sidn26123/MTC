package com.sidn.metruyenchu.paymentservice.entity;

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
 * Entity đại diện cho một yêu cầu thanh toán do người dùng khởi tạo.
 * <p>
 * Được tạo khi người dùng yêu cầu nạp tiền vào ví. Lưu trữ thông tin về phương thức thanh toán,
 * số tiền, đơn vị tiền tệ và các URL cần thiết để xử lý luồng thanh toán (callback, redirect).
 * <p>
 * Sau khi bên thứ ba xử lý thanh toán thành công, hệ thống sẽ sử dụng bản ghi này để tạo giao dịch thực tế.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    String id;

    @Column(name = "request_code", nullable = false, unique = true)
    String requestCode;

    @Column(name = "user_id", nullable = false)
    String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    PaymentMethod paymentMethod;

    @Column(nullable = false, precision = 19, scale = 2)
    BigDecimal amount;

    @Column(name = "currency_id", nullable = false)
    String currencyId;

    @Column(name = "callback_url", length = 2048)
    String callbackUrl;

    @Column(name = "return_url", length = 2048)
    String returnUrl;

    @Column(name = "payment_url", length = 2048)

    String paymentUrl;

    @Column(name = "expires_at")
    LocalDateTime expiresAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Column(name = "completed_at")
    LocalDateTime completedAt;
}
