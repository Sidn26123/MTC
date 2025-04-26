package com.sidn.metruyenchu.fileservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterContentGetRequest {
    String novelId;
    String chapterId;

}
