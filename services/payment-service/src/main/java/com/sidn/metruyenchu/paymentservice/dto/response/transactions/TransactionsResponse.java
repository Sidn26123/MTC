package com.sidn.metruyenchu.paymentservice.dto.response.transactions;
import com.sidn.metruyenchu.paymentservice.enums.TransactionStatus;
import com.sidn.metruyenchu.paymentservice.enums.TransactionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionsResponse {
    String id;
    String transactionCode;
    String userId;
    String walletId;
    TransactionType type;
    Integer amount;
    String currencyId;
    TransactionStatus status;
    String referenceId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime completedAt;
}