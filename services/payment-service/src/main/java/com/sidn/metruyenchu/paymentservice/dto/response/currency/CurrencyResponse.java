package com.sidn.metruyenchu.paymentservice.dto.response.currency;
import com.sidn.metruyenchu.paymentservice.enums.CurrencyStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyResponse {
    String id;
    String code;
    Integer amount;
    String name;
    CurrencyStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
