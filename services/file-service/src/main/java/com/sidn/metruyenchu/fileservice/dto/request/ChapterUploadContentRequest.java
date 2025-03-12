package com.sidn.metruyenchu.fileservice.dto.request;


import com.sidn.metruyenchu.fileservice.enums.FilePurposeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterUploadContentRequest {
    @NonNull
    String novelId;
    String chapterId;
    String displayName;
    String fileName;
    String relativePath;
    @Builder.Default
    FilePurposeEnum type = FilePurposeEnum.CHAPTER_CONTENT;
}
