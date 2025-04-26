package com.sidn.metruyenchu.novelservice.dto.response;

import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelState;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import com.sidn.metruyenchu.novelservice.enums.ProgressStatus;
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
    NovelAuthorResponse author;
    List<NovelStatusResponse> status;
    List<GenreResponse> genres;
    List<WorldSceneResponse> worldScenes;
    List<MainCharacterTraitResponse> mainCharacterTraits;
    List<SectResponse> sects;

    NovelType novelType;
    NovelVisibility novelVisibility;
    NovelState novelState;
    ProgressStatus progressStatus;

    Integer chapterReadToComment;
    Integer chapterReadToRate;
    Integer fullSetPurchaseDiscount;
    Integer wordCount;
    Float avgRate;
    Integer totalRates;
    Integer totalComments;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted = false;
    Boolean isActive = true;


}
