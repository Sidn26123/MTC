package com.sidn.metruyenchu.shared_library.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonStatisticDto {
    private Long currentPeriod;
    private Long previousPeriod;
    private Double changePercentage;
    private String trend; // "INCREASE", "DECREASE", "STABLE"
}