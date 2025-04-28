package com.sidn.metruyenchu.novelservice.dto.response.novel;

import com.sidn.metruyenchu.novelservice.dto.response.GenreResponse;
import com.sidn.metruyenchu.novelservice.dto.response.MainCharacterTraitResponse;
import com.sidn.metruyenchu.novelservice.dto.response.SectResponse;
import com.sidn.metruyenchu.novelservice.dto.response.WorldSceneResponse;
import com.sidn.metruyenchu.novelservice.enums.NovelState;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import com.sidn.metruyenchu.novelservice.enums.ProgressStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelBasicInfoResponse {
    String id;
    String name;
    String displayName;
    String slug;
    String description;
    String novelCoverImage;
    String currentPublisher;
    NovelAuthorResponse author;

    List<GenreResponse> genres;

    Integer totalChapters;

    Boolean isDeleted = false;
    Boolean isActive = true;
}
