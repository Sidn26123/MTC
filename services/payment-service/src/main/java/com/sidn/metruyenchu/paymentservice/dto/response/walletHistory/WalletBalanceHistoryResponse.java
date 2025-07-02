package com.sidn.metruyenchu.paymentservice.dto.response.walletHistory;

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
public class WalletBalanceHistoryResponse {
     String id;
     String walletId;
     String transactionId;
     String userId;
     BigDecimal balanceBefore;
     BigDecimal balanceAfter;
     BigDecimal amountChanged;
     TransactionType transactionType;
     String transactionReference;
     String currencyId;
     String currencyCode;
     LocalDateTime createdAt;
     String reason;
     String ipAddress;
     String userAgent;
     String requestId;
}
