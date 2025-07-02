package com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequestResponse {
    String id;
    String requestCode;
    String userId;
    String paymentMethodId;
    BigDecimal amount;
    String currencyId;
    String callbackUrl;
    String returnUrl;
    String paymentUrl;
    LocalDateTime expiresAt;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime completedAt;
}
