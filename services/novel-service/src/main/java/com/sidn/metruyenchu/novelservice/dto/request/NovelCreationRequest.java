package com.sidn.metruyenchu.novelservice.dto.request;

import com.sidn.metruyenchu.novelservice.entity.NovelStatusModerationStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelCreationRequest {
    String displayName;

    @Builder.Default
    String description = "N/A";
    String novelCoverImage;

    @Builder.Default
    String originalName = "N/A";

    @Builder.Default
    String originalLink = "N/A";
    String currentPublisher;

    String authorId;

    NovelStatusModerationStatus moderationStatus;

    @Enumerated(EnumType.STRING)
    NovelType novelType = NovelType.COMPOSE;

    @Enumerated(EnumType.STRING)
    NovelVisibility novelVisibility = NovelVisibility.PRIVATE;



}
