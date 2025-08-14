package com.sidn.metruyenchu.feedbackservice.dto.response.stat;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelCommentPieChartDto {
    private String novelId;
    private String novelTitle; // Sẽ được populate từ service khác
    private Long commentCount;
    private Double percentage;
}