package com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BulkChapterPurchaseRequest {
    String novelId;                 // hoặc novelSlug
    Integer startChapterIndex;     // chương bắt đầu
    Integer quantity;              // số chương muốn mua (có phí)
    String userId;
    String currencyId;
    BigDecimal discount;
}
