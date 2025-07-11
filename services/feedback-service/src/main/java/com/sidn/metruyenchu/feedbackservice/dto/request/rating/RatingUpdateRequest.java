package com.sidn.metruyenchu.feedbackservice.dto.request.rating;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingUpdateRequest {
    String content;
    String novelId;
    Integer lastReadChapterId;
    Float rate;
    Integer totalLikes;
    Integer totalDislikes;
    Boolean isDeleted;
    Boolean isHidden;
}
