package com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase;
import com.sidn.metruyenchu.shared_library.enums.payment.ContentType;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
/**
 * Request DTO for content purchases
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentPurchaseRequest {
    @NotBlank(message = "Item type is required")
    ContentType itemType;
    
    @NotBlank(message = "Item ID is required")
    String itemId;
    
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be non-negative")
    BigDecimal price;
    
    @NotBlank(message = "Currency ID is required")
    String currencyId;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    Integer quantity;
    
    BigDecimal discount;

    String userId;

    TransactionType transactionType;
}