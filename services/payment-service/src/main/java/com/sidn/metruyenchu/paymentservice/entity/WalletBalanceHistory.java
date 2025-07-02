package com.sidn.metruyenchu.paymentservice.entity;

import com.sidn.metruyenchu.paymentservice.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "wallet_balance_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class WalletBalanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "wallet_id", nullable = false)
    String walletId; //mục đích chủ yếu là audit, không cần truy vấn thường xuyên

    @Column(name = "transaction_id")
    String transactionId; // mục đích chủ yếu là audit, không cần truy vấn thường xuyên

    @Column(name = "user_id", nullable = false)
    String userId;

    // Balance tracking
    @Column(name = "balance_before", precision = 18, scale = 8, nullable = false)
    BigDecimal balanceBefore;

    @Column(name = "balance_after", precision = 18, scale = 8, nullable = false)
    BigDecimal balanceAfter;

    @Column(name = "amount_changed", precision = 18, scale = 8, nullable = false)
    BigDecimal amountChanged;

    // Transaction context
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    TransactionType transactionType;

    @Column(name = "transaction_reference")
    String transactionReference;

    // Currency info
    @Column(name = "currency_id", nullable = false)
    String currencyId;

//    @Column(name = "currency_code", nullable = false)
//    String currencyCode;

    // Audit fields
//    @Column(name = "created_by")
//    String createdBy;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "reason")
    String reason;

    // Metadata
    @Column(name = "ip_address")
    String ipAddress;

//    @Column(name = "user_agent")
//    String userAgent;
//
//    @Column(name = "request_id")
//    String requestId;
}