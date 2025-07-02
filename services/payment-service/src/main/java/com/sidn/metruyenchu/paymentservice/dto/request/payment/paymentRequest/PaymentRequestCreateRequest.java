package com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequestCreateRequest {
    String requestCode;
    String userId;
    String paymentMethodId;
    BigDecimal amount;
    String currencyId;
    String callbackUrl;
    String returnUrl;
    LocalDateTime expiresAt;
}