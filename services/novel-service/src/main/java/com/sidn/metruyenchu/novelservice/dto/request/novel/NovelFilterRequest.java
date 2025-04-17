package com.sidn.metruyenchu.novelservice.dto.request.novel;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.enums.NovelState;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import com.sidn.metruyenchu.novelservice.enums.ProgressStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String currenPublisher;
    List<String> sects;
    List<String> worldScenes;
    List<String> mainCharacterTraits;
    List<String> genres;
    List<String> status;

    List<Integer> chapterReadToCommentRange;
    List<Integer> chapterReadToRateRange;
    List<Integer> wordCountRange;

    List<NovelType> novelTypes;
    List<NovelVisibility> novelVisibilities;
    List<ProgressStatus> progressStatuses;
    List<NovelState> novelStates;



    Boolean isPublished;
    //Với các field có kiểu dữ liệu là int, [;]
//    String sortBy = "created_at";
//    String sortOrder = "DESC";
//    int page = 1;
//    int size = 10;
}
