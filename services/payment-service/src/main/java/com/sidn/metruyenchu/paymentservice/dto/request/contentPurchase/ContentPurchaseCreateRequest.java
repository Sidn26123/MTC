package com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentPurchaseCreateRequest {
    String transactionId;
    String itemType;
    String itemId;
    BigDecimal price;
    String currencyId;
    Integer quantity;
    BigDecimal discount;
}