package com.sidn.metruyenchu.paymentservice.entity;

import com.sidn.metruyenchu.paymentservice.enums.TransactionStatus;
import com.sidn.metruyenchu.paymentservice.enums.TransactionType;
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
 * Entity đại diện cho một giao dịch tài chính đã được thực hiện trong hệ thống.
 *
 * Giao dịch có thể là nạp tiền (DEPOSIT), mua nội dung (PURCHASE), hoặc các loại giao dịch khác.
 * Mỗi giao dịch liên kết với ví người dùng và có thông tin về số tiền, trạng thái,
 * loại giao dịch và đối tượng liên quan (ví dụ: mua chương, mua VIP).
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "transaction_code", nullable = false, unique = true)
    String transactionCode;

    @Column(name = "user_id", nullable = false)
    String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TransactionType type;

    @Column(nullable = false)
    Integer amount;

    @Column(name = "currency_id", nullable = false)
    String currencyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    TransactionStatus status = TransactionStatus.PENDING;

    @Column(name = "reference_id")
    String referenceId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Column(name = "completed_at")
    LocalDateTime completedAt;

    @OneToOne(mappedBy = "transaction")
    ContentPurchase contentPurchase;

//    /**
//     * Transaction type enum.
//     */
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    TransactionType transactionType;
//
//    /**
//     * Transaction status enum.
//     */
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    TransactionStatus transactionStatus;
}
