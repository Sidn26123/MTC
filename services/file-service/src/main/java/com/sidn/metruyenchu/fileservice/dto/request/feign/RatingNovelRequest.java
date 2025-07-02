package com.sidn.metruyenchu.fileservice.dto.request.feign;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingNovelRequest {
    String novelId;

    Float rating;

    String ratedBy;

    
}
