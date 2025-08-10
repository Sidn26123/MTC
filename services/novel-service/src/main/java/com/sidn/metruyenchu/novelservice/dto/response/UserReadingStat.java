package com.sidn.metruyenchu.novelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReadingStat {
    private String userId;
    private Long totalLogs;
    private Long totalDuration;
    private Long finishedCount;
}