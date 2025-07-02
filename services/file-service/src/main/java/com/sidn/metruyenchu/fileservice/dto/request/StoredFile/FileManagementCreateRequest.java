package com.sidn.metruyenchu.fileservice.dto.request.StoredFile;

import com.sidn.metruyenchu.fileservice.enums.FileCategoryEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileManagementCreateRequest {
    String displayName;
    String path;
    String relativePath;
    String fileName;
    String extension;
    FileCategoryEnum type;
    MultipartFile file;

}
