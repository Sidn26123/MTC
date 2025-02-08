package com.sidn.metruyenchu.feedbackservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreationRequest {

    String content;
    String commenter;
    String targetId;

    String commentInNovelId;

    String commentInChapterId;

    String commentParentId;

}
