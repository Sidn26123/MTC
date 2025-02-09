package com.sidn.metruyenchu.feedbackservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    String id;
    String content;
    String reviewer;
    String reviewInNovelId;
    Integer lastReadChapterId;
    Float rating;
    Integer likeCount;
    Integer dislikeCount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted;
    Boolean isHidden;
}
