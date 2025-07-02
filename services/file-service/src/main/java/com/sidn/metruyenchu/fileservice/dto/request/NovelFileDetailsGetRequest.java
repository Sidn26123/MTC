package com.sidn.metruyenchu.fileservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelFileDetailsGetRequest {
    String novelId;

    int page = 1;
    int size = 10;
}
