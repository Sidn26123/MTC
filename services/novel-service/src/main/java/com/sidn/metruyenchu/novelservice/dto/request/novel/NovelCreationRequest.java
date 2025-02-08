package com.sidn.metruyenchu.novelservice.dto.request.novel;

import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelCreationRequest {
    String name;

    String displayName;

    String slug;

    @Builder.Default
    String description = "N/A";

    @Builder.Default
    String novelCoverImage = "N/A";

    @Builder.Default
    String originalName = "N/A";

    @Builder.Default
    String originalLink = "N/A";

    String currentPublisher;

    String authorId;

    Set<String> status;

    @Enumerated(EnumType.STRING)
    NovelType novelType = NovelType.COMPOSE;

    @Enumerated(EnumType.STRING)
    NovelVisibility novelVisibility = NovelVisibility.PRIVATE;



}
