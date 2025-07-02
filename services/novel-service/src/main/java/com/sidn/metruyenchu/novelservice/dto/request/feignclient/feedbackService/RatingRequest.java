package com.sidn.metruyenchu.novelservice.dto.request.feignclient.feedbackService;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingRequest {
    String novelId;
    Float rate;
    Float oldRate;
    Boolean isNew;
}
