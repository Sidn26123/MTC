package com.sidn.metruyenchu.paymentservice.dto.response.contentPurchase;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentPurchaseResponse {
    String id;
    String transactionId;
    String itemType;
    String itemId;
    BigDecimal price;
    BigDecimal finalPrice;
    String currencyId;
    Integer quantity;
    BigDecimal discount;
    LocalDateTime createdAt;
}