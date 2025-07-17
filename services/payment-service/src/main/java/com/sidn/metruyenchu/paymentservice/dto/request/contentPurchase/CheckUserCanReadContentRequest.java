package com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase;

import com.sidn.metruyenchu.shared_library.enums.payment.ContentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckUserCanReadContentRequest {
    String userId;
    String chapterId;
    String novelId;
}
