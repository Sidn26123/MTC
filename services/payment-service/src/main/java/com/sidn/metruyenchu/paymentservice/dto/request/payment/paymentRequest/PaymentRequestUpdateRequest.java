package com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentRequest;
import com.sidn.metruyenchu.shared_library.enums.payment.PaymentRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequestUpdateRequest {
    String paymentUrl;
    PaymentRequestStatus status;
    LocalDateTime completedAt;
}