package com.sidn.metruyenchu.fileservice.dto.response.Chapter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterContentResponse {
//    String id;
    String novelId;
    String content;
}
