package com.sidn.metruyenchu.fileservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelFileUpdateRequest {
    String novelId;
    String chapterId;
    String displayName;
    String fileName;
    String relativePath;
    String type;
    Boolean isDeleted;
    MultipartFile file;
}
