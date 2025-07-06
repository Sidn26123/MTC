package com.sidn.metruyenchu.novelservice.dto.request.statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelApprovalStatisticDto {
    private Long totalApproved;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}