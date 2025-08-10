package com.sidn.metruyenchu.paymentservice.dto.response.transactions;
import com.sidn.metruyenchu.paymentservice.dto.response.currency.CurrencyResponse;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionType;
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
    CurrencyResponse currency;
    TransactionStatus status;
    String referenceId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime completedAt;
}