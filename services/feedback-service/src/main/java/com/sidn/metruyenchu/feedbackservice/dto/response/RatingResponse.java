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
    String novelId;
    String lastReadChapterId;
    Float rate;
    Integer totalLikes;
    Integer totalDislikes;
    Integer totalComments;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted;
    Boolean isHidden;
}
