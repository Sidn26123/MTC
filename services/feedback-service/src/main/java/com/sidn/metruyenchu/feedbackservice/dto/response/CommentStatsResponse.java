package com.sidn.metruyenchu.feedbackservice.dto.response;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentStatsResponse {
    int label; // month hoặc day
    long value;
}