package com.sidn.metruyenchu.fileservice.dto.response;

import com.sidn.metruyenchu.fileservice.enums.FileCategoryEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelFileResponse {
    String id;
    String novelId;
    String chapterId;
    String relativePath;
    FileCategoryEnum category;
}
