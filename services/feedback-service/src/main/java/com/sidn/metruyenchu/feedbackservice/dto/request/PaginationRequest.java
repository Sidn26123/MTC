package com.sidn.metruyenchu.feedbackservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationRequest {
    int page = 1;
    int size = 10;
    String sort = "createdAt";
    String direction = "desc";
}
