package com.sidn.metruyenchu.feedbackservice.dto.response.projection;

import lombok.*;
import lombok.experimental.FieldDefaults;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
public interface GeneralCountProjectionResponse {
//    String id;
//    Integer count;
    String getId();
    Integer getCount();
}
