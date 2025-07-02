package com.sidn.metruyenchu.paymentservice.dto.request.transactions;
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
public class TransactionsCreateRequest {
    String transactionCode;
    String userId;
    String walletId;
    TransactionType type;
    Integer amount;
    String currencyId;
}