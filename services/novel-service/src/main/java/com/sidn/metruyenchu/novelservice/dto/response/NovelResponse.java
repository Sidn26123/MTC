package com.sidn.metruyenchu.novelservice.dto.response;

import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelResponse {
    String id;
    String name;
    String displayName;
    String slug;
    String description;
    String novelCoverImage;
    String originalName;
    String originalLink;
    String currentPublisher;
    String authorId;
    List<NovelStatusResponse> status;
    NovelType novelType;
    NovelVisibility novelVisibility;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted = false;
    Boolean isActive = true;


}
