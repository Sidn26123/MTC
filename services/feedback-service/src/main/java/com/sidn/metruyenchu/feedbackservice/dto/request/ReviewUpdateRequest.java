package com.sidn.metruyenchu.feedbackservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewUpdateRequest {
    String content;
    String reviewInNovelId;
    Integer lastReadChapterId;
    Float rating;
    Integer likeCount;
    Integer dislikeCount;
    Boolean isDeleted;
    Boolean isHidden;
}
