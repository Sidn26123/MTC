package com.sidn.metruyenchu.novelservice.dto.response.novel;

import com.sidn.metruyenchu.novelservice.dto.response.GenreResponse;
import com.sidn.metruyenchu.novelservice.dto.response.MainCharacterTraitResponse;
import com.sidn.metruyenchu.novelservice.dto.response.SectResponse;
import com.sidn.metruyenchu.novelservice.dto.response.WorldSceneResponse;
import com.sidn.metruyenchu.novelservice.enums.ProgressStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelState;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

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
    String publisherNote;
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
    String novelTypeLabel;
    NovelVisibility novelVisibility;
    String novelVisibilityLabel;
    NovelState novelState;
    String novelStateLabel;
    ProgressStatus progressStatus;
    String progressStatusLabel;

    Integer chapterReadToComment;
    Integer chapterReadToRate;
    Integer fullSetPurchaseDiscount;
    Integer wordCount;
    Float avgRate;
    Integer totalRates;
    Integer totalComments;
    Integer totalChapters;
    Integer totalViews;
    Integer totalBookmarks;
    Integer totalPromotions;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted = false;
    Boolean isActive = true;


}
