package com.sidn.metruyenchu.novelservice.dto.request.statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterViewDistributionDto {
    private String viewRange; // "0-100", "101-500", etc.
    private Long chapterCount;
}