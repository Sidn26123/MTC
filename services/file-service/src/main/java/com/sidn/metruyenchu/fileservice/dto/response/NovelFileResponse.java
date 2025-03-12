package com.sidn.metruyenchu.fileservice.dto.response;

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
    String displayName;
    String fileName;
    String relativePath;
    String type;
}
