package com.sidn.metruyenchu.novelservice.dto.response;

import com.sidn.metruyenchu.novelservice.entity.NovelStatusModerationStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelResponse {
    String id;
    String name;
    String displayName;
    String description;
    String novelCoverImage;
    String originalName;
    String originalLink;
    String currentPublisher;
    String authorId;
    NovelStatusModerationStatus moderationStatus;
    NovelType novelType;
    NovelVisibility novelVisibility;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted = false;


}
