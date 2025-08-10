package com.sidn.metruyenchu.fileservice.dto.request.feign;

import com.sidn.metruyenchu.shared_library.enums.novel.NovelType;
import com.sidn.metruyenchu.shared_library.enums.novel.NovelVisibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelUpdateRequest {
    String name;

    String displayName;

    String slug;

    String publisherNote;

    String description;

    String novelCoverImage;
}