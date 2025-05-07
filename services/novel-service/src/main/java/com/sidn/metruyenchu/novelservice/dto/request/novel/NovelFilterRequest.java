package com.sidn.metruyenchu.novelservice.dto.request.novel;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.enums.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelFilterRequest extends BaseFilterRequest {
    String searchText;
    String authorId;
    String slug;
    String novelId;
    String originName;
    String currentPublisher;
    List<String> sects;
    List<String> worldScenes;
    List<String> mainCharacterTraits;
    List<String> genres;
    List<String> status;

    List<NovelType> novelTypes;
    List<NovelVisibility> novelVisibilities;
    List<ProgressStatus> progressStatuses;
    List<NovelState> novelStates;
    List<NovelAttribute> novelAttributes;

    Integer chapterReadToCommentFrom;
    Integer chapterReadToCommentTo;

    Integer chapterReadToRateFrom;
    Integer chapterReadToRateTo;

    Integer wordCountFrom;
    Integer wordCountTo;

    Float avgRateFrom;
    Float avgRateTo;

    Integer totalRatesFrom;
    Integer totalRatesTo;

    Integer totalCommentsFrom;
    Integer totalCommentsTo;

    LocalDateTime createdAtFrom;
    LocalDateTime createdAtTo;
    LocalDateTime updatedAtFrom;
    LocalDateTime updatedAtTo;


    Boolean isPublished;
    Boolean isDeleted;
    //Với các field có kiểu dữ liệu là int, [;]
//    String sortBy = "created_at";
//    String sortOrder = "DESC";
//    int page = 1;
//    int size = 10;
}
