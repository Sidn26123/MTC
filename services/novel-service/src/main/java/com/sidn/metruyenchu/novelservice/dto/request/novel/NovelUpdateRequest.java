package com.sidn.metruyenchu.novelservice.dto.request.novel;

import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelUpdateRequest {
    String name;

    String displayName;

    String slug;

    String description;

    String novelCoverImage;

    String originalName;

    String originalLink;

    String currentPublisher;

    String authorId;

    Set<String> status;

    NovelType novelType;

    NovelVisibility novelVisibility;
}
