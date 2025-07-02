package com.sidn.metruyenchu.feedbackservice.dto.request.comment;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentUpdateRequest {
    String content;

    String commentedBy;

    String parentId;

    FeedbackType feedbackType;

    String novelId;

    String chapterId;

    Integer totalLikes;

    Integer totalDisLikes;

    Boolean isDeleted;

    Boolean isHidden;
}
