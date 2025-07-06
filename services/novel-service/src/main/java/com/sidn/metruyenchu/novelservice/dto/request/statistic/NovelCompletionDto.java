package com.sidn.metruyenchu.novelservice.dto.request.statistic;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelCompletionDto {
    private String novelId;
    private String novelName;
    private Long totalReaders;
    private Long completedReaders;
    private Double completionRate;
    private Integer totalChapters;
}