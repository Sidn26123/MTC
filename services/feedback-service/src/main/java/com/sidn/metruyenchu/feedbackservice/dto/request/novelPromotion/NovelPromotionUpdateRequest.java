package com.sidn.metruyenchu.feedbackservice.dto.request.novelPromotion;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelPromotionUpdateRequest {
    String id;
    String novelId;
    String chapterId;
}
