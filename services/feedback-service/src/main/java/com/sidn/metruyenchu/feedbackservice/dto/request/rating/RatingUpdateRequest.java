package com.sidn.metruyenchu.feedbackservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingUpdateRequest {
    String content;
    String reviewInNovelId;
    Integer lastReadChapterId;
    Float rating;
    Integer totalLikes;
    Integer totalDislikes;
    Boolean isDeleted;
    Boolean isHidden;
}
