package com.sidn.metruyenchu.shared_library.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeRangeStatisticDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long total;
}