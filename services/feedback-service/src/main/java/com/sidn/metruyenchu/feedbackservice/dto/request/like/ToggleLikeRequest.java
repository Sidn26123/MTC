package com.sidn.metruyenchu.feedbackservice.dto.request.like;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToggleLikeRequest {
    String userId;
    String parentId; // This can be a commentId or ratingId
    FeedbackType parentType; // This can be "comment" or "rating"
}
