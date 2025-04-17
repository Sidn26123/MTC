package com.sidn.metruyenchu.fileservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageableRequest {
    @Builder.Default
    int page = 0;
    @Builder.Default
    int size = 10;
    @Builder.Default
    String sort = "id";
    @Builder.Default
    Sort.Direction direction = Sort.Direction.ASC;
}
