package com.sidn.metruyenchu.feedbackservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingResponse {
    String id;
    String content;
    String ratedBy;
    String reviewInNovelId;
    Integer lastReadChapterId;
    Float rating;
    Integer totalLikes;
    Integer totalDislikes;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted;
    Boolean isHidden;
}
