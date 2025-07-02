package com.sidn.metruyenchu.novelservice.dto.request.novel;

import com.sidn.metruyenchu.novelservice.enums.NovelState;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
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

    String publisherNote;

    String description;

    String novelCoverImage;

    String originalName;

    String originalLink;

    String currentPublisher;

    String authorId;


    List<String> status;

    NovelType novelType;

    NovelVisibility novelVisibility;

    NovelState novelState;

    List<String> genreIds;

    List<String> mainCharacterTraitIds;

    List<String> sectIds;

    List<String> worldSceneIds;

    List<String> statusIds;

    Integer chapterReadToComment;

    Integer chapterReadToRate;

    Integer fullSetPurchaseDiscount;

    Float avgRate;

    Integer totalComments;

    Integer totalRates;

    Integer totalChapters;

}
