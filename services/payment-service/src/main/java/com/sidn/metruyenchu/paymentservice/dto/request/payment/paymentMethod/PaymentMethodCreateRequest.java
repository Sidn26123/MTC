package com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod;
import com.sidn.metruyenchu.paymentservice.enums.PaymentMethodStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethodCreateRequest {
    String code;
    PaymentMethodStatus status;
}