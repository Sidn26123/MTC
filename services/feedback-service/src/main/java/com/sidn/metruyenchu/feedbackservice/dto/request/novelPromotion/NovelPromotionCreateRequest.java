package com.sidn.metruyenchu.feedbackservice.dto.request.novelPromotion;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelPromotionCreateRequest {
    String novelId;
    String chapterId;
    String userId;

}
