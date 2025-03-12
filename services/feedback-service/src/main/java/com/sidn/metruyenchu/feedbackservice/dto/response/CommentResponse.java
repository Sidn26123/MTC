package com.sidn.metruyenchu.feedbackservice.dto.response;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String id;
    String content;
    String commentedBy;
    String parentId;
    FeedbackType feedbackType;
    String commentInNovelId;
    String commentInChapterId;
    String commentParentId;
    Integer totalLikes;
    Integer totalDisLikes;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted;
    Boolean isHidden;
}
