package com.sidn.metruyenchu.novelservice.dto.request.statistic;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopChapterDto {
    private String chapterId;
    private String chapterName;
    private String novelId;
    private String novelName;
    private Long bookmarkCount;
    private Long viewCount;
    private Integer rank;
}