package com.sidn.metruyenchu.feedbackservice.dto.response.novelPromotion;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelPromotionResponse {
    String id;
    String userId;
    String chapterId;
    String createdAt;
}
