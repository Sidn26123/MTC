package com.sidn.metruyenchu.feedbackservice.dto.request.comment;

import com.sidn.metruyenchu.feedbackservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentFilterRequest extends BaseFilterRequest {
    String novelId;
    String chapterId;
    String parentId;
    String commentedBy;
    FeedbackType feedbackType;
    Boolean isDeleted;
    Boolean isHidden;
    LocalDateTime createdAfter;
    LocalDateTime createdBefore;
    LocalDateTime updatedAfter;
    LocalDateTime updatedBefore;
    Boolean isGetChildCommentCount;
    Boolean isGetLikeCount;
}
