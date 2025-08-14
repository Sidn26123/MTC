package com.sidn.metruyenchu.feedbackservice.dto.response.stat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Builder
public class PieChartItem {
    String label;
    long value;
}