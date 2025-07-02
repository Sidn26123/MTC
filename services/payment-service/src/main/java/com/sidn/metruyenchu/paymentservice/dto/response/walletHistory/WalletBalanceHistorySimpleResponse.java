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
public class WalletBalanceHistorySimpleResponse {
    private String id;
    private String walletId;
    private String transactionId;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private BigDecimal amountChanged;
    private TransactionType transactionType;
    private String currencyCode;
    private LocalDateTime createdAt;
    private String reason;
}