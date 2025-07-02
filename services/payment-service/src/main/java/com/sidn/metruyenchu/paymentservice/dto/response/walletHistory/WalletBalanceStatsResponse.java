package com.sidn.metruyenchu.paymentservice.dto.response.walletHistory;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletBalanceStatsResponse {
    private String walletId;
    private String currencyCode;
    private BigDecimal currentBalance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private Long transactionCount;
    private LocalDateTime lastTransactionAt;
}
