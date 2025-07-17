package com.sidn.metruyenchu.novelservice.dto.request.statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordCountStatisticDto {
    private Long totalWordsWritten;
    private Long totalWordsAllTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}