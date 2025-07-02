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
    String novelId;
    String chapterId;
    String commentParentId;
    @Builder.Default
    Integer totalComments = 0; //total child comments
    Integer totalLikes;
    @Builder.Default
    Integer totalLike = 0;
    Integer totalDisLikes;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted;
    Boolean isHidden;
}
