package com.sidn.metruyenchu.feedbackservice.dto.response;

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
    String commenter;

    String commentInNovelId;
    String commentInChapterId;
    String commentParentId;

    Integer likeCount;
    Integer replyCount;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    Boolean isDeleted;
    Boolean isHidden;
}
