package com.sidn.metruyenchu.paymentservice.dto.request.walletHistory;

import com.sidn.metruyenchu.paymentservice.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class WalletBalanceHistoryCreateRequest {
    @NotBlank(message = "Wallet ID is required")
    String walletId;

    String transactionId;

    @NotBlank(message = "User ID is required")
    String userId;

    @NotNull(message = "Balance before is required")
    @DecimalMin(value = "0.0", message = "Balance before must be non-negative")
    BigDecimal balanceBefore;

    @NotNull(message = "Balance after is required")
    @DecimalMin(value = "0.0", message = "Balance after must be non-negative")
    BigDecimal balanceAfter;

    @NotNull(message = "Amount changed is required")
    BigDecimal amountChanged;

    @NotNull(message = "Transaction type is required")
    TransactionType transactionType;

    String transactionReference;

    @NotBlank(message = "Currency ID is required")
    String currencyId;

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be 3 characters")
    String currencyCode;

    String reason;
    String ipAddress;
    String userAgent;
    String requestId;

}