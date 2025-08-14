package com.sidn.metruyenchu.feedbackservice.dto.response.stat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Builder
public class HeatmapItem {
    int dayOfWeek; // 1=Monday, 7=Sunday
    int hour; // 0-23
    long count;
}
