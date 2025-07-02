package com.sidn.metruyenchu.novelservice.dto.request.chapter;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterContentGetRequest {
    String chapterId;
    String novelSlug;
    Integer chapterIdx;

    Integer startIdx;
    @Builder.Default
    Integer charTotalToGet = 1000;
}
