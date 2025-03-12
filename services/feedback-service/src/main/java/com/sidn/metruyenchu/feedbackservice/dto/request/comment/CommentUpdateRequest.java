package com.sidn.metruyenchu.feedbackservice.dto.request;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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

    String commentedInNovelId;

    String commentedInChapterId;

    Integer totalLikes;

    Integer totalDisLikes;

    Boolean isDeleted;

    Boolean isHidden;
}
