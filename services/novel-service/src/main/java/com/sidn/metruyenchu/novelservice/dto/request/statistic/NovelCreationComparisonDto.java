package com.sidn.metruyenchu.novelservice.dto.request.statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelCreationComparisonDto {
    private Long currentPeriodTotal;
    private Long previousPeriodTotal;
    private Double changePercentage;
    private String period; // "DAY", "WEEK", "MONTH"
    private LocalDateTime currentStartTime;
    private LocalDateTime currentEndTime;
}