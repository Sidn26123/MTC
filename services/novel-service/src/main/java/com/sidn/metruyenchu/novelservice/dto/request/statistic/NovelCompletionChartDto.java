package com.sidn.metruyenchu.novelservice.dto.request.statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelCompletionChartDto {
    private String completionRange; // "0-20%", "21-40%", etc.
    private Long novelCount;
}