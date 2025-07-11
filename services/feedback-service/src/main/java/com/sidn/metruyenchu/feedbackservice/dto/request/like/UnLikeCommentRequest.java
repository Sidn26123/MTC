package com.sidn.metruyenchu.feedbackservice.dto.request.like;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnLikeCommentRequest {
    String commentId;
    String userId;
}
