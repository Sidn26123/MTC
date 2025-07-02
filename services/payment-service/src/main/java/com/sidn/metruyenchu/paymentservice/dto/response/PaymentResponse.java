package com.sidn.metruyenchu.paymentservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Response DTO for payment operations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String requestId;
    String transactionId;
    String paymentUrl;
    LocalDateTime expiresAt;
    String status;
}