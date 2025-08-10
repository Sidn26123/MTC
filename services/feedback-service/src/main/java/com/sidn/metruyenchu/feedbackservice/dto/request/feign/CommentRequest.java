package com.sidn.metruyenchu.feedbackservice.dto.request.feign;

import com.sidn.metruyenchu.shared_library.enums.feedback.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    FeedbackType feedbackType;
    String parentId;
}