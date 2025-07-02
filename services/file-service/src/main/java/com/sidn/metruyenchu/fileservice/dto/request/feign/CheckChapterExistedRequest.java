package com.sidn.metruyenchu.fileservice.dto.request.feign;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckChapterExistedRequest {
    String id;
    String slug;
}
