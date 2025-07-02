package com.sidn.metruyenchu.fileservice.dto.request.StoredFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchFileRequest {
    String keyword;
    String categoryId;
    @Builder.Default
    PageRequest pageRequest = new PageRequest(0, 10);
}
