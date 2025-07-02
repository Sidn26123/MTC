package com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentMethod;
import com.sidn.metruyenchu.paymentservice.enums.PaymentMethodStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethodResponse {
    String id;
    String code;
    PaymentMethodStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}