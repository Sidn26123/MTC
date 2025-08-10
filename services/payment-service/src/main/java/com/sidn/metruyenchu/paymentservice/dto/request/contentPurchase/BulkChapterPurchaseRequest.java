package com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BulkChapterPurchaseRequest {
    String novelId;                 // hoặc novelSlug
    Integer startChapterIndex;     // chương bắt đầu
    Integer quantity;              // số chương muốn mua (có phí)
    String userId;
    String currencyId;
    BigDecimal discount;
    List<String> chapterIds;

    // Validation
    @AssertTrue(message = "Chapter IDs cannot be empty")
    public boolean isChapterIdsValid() {
        return chapterIds != null && !chapterIds.isEmpty();
    }
}
