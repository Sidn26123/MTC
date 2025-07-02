package com.sidn.metruyenchu.paymentservice.entity;

import com.sidn.metruyenchu.paymentservice.enums.PaymentMethodStatus;
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
import java.util.List;

/**
 * Entity đại diện cho các phương thức thanh toán được hệ thống hỗ trợ, ví dụ: VNPay, Momo, v.v.
 * <p>
 * Mỗi phương thức có mã định danh duy nhất và trạng thái hoạt động. Quản trị viên có thể bật/tắt
 * từng phương thức theo nhu cầu. Dữ liệu này giúp hiển thị danh sách phương thức thanh toán cho người dùng.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    String id;

    @Column(nullable = false, unique = true)
    String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    PaymentMethodStatus status = PaymentMethodStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "paymentMethod")
    List<PaymentRequest> paymentRequests;

//    /**
//     * Payment method status enum.
//     */
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    PaymentMethodStatus paymentMethodStatus;
}
