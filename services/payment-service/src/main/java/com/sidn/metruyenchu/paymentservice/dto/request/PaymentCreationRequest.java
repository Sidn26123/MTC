package com.sidn.metruyenchu.paymentservice.dto.request;

import com.sidn.metruyenchu.paymentservice.enums.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentCreationRequest {
    String orderId;
    PaymentMethod paymentMethod;
    BigDecimal paymentAmount;
    String paymentStatus;
    LocalDate paymentDate;
    String paymentDescription;
    CustomerRequest customer;
}
