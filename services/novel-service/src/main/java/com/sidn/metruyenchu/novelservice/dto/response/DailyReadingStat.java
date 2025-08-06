package com.sidn.metruyenchu.novelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReadingStat {
    private LocalDate date;
    private Long totalLogs;
    private Long totalDuration;
    private Long finishedCount;
    
    // Constructor cho native query
    public DailyReadingStat(Object date, Object totalLogs, Object totalDuration, Object finishedCount) {
        this.date = (LocalDate) date;
        this.totalLogs = ((Number) totalLogs).longValue();
        this.totalDuration = ((Number) totalDuration).longValue();
        this.finishedCount = ((Number) finishedCount).longValue();
    }
}