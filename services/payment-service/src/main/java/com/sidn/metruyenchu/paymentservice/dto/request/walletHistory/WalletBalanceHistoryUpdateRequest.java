package com.sidn.metruyenchu.paymentservice.dto.request.walletHistory;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class WalletBalanceHistoryUpdateRequest {
     String transactionReference;
     String reason;
     String ipAddress;
     String userAgent;
     String requestId;
}