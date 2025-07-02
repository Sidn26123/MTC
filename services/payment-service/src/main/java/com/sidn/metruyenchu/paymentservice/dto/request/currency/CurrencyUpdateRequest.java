package com.sidn.metruyenchu.paymentservice.dto.request.currency;
import com.sidn.metruyenchu.paymentservice.enums.CurrencyStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyUpdateRequest {
    Integer amount;
    CurrencyStatus status;
}