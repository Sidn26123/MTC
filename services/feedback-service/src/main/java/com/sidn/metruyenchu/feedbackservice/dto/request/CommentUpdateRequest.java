package com.sidn.metruyenchu.feedbackservice.dto.request;

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

    String commentInNovelId;
    String commentInChapterId;
    String commentParentId;

    Integer likeCount;
    Integer replyCount;

    Boolean isDeleted;
    Boolean isHidden;
}
