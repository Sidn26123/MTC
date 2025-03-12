package com.sidn.metruyenchu.fileservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelFileUpdateRequest {
//    String novelId;
//    String chapterId;
    String displayName;
    String fileName;
    String relativePath;
    String type;
    Boolean isDeleted;
}
