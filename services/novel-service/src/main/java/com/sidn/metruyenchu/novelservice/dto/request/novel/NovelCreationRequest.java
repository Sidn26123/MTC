package com.sidn.metruyenchu.novelservice.dto.request.novel;

import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import com.sidn.metruyenchu.novelservice.enums.ProgressStatus;
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


    @Enumerated(EnumType.STRING)
    @Builder.Default
    NovelType novelType = NovelType.COMPOSE;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    NovelVisibility novelVisibility = NovelVisibility.PRIVATE;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    ProgressStatus progressStatus = ProgressStatus.IN_PROGRESS;

    @Builder.Default
    Integer chapterReadToComment = 0;

    @Builder.Default
    Integer chapterReadToRate = 10;

    @Builder.Default
    Integer fullSetPurchaseDiscount = 0;

    List<String> status;

    List<String> genreIds;

    List<String> mainCharTraitIds;

    List<String> sectIds;

    List<String> worldSceneIds;

//    List<String> statusIds;


}
