package com.sidn.metruyenchu.feedbackservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewCreationRequest {
    String content;

    String reviewer;

    String targetId;

    String reviewInNovelId;

    String lastReadChapterId;

    Float rating;

}
