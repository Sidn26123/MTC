package com.sidn.metruyenchu.fileservice.dto.request;


import com.sidn.metruyenchu.fileservice.enums.FileCategoryEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterContentUploadRequest {
    String novelId;
    String chapterId;
    String displayName;
    String fileName;
    String relativePath;
    String path;
    String content;
    MultipartFile file;
    @Builder.Default
    FileCategoryEnum type = FileCategoryEnum.CHAPTER_CONTENT;
}
