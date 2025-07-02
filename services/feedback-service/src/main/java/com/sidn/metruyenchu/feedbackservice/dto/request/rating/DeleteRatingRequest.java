package com.sidn.metruyenchu.feedbackservice.dto.request.rating;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteRatingRequest {
    String ratingId;
    String novelId;
    Float rate;
}
