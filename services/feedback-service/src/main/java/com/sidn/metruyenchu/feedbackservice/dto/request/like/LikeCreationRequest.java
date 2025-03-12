package com.sidn.metruyenchu.feedbackservice.dto.request.like;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeCreationRequest {
    String likedBy;

    String parentId;

    FeedbackType feedbackType;

    Boolean isLiked;


}
