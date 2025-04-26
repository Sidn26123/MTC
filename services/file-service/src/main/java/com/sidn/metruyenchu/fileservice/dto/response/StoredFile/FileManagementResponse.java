package com.sidn.metruyenchu.fileservice.dto.response.StoredFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileManagementResponse {
    String id;
    String displayName;
    String relativePath;
    String fileName;
    String extension;

    boolean isDeleted;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;


}
