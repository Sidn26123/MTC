package com.sidn.metruyenchu.fileservice.dto.request;

import com.sidn.metruyenchu.fileservice.enums.FileCategoryEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilePathGetRequest {
    String fileId;
    String novelId;
    String chapterId;
    String fileName;

    FileCategoryEnum type;

}
