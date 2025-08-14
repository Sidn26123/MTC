package com.sidn.metruyenchu.feedbackservice.dto.response.stat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentTimelineDto {
    private String timeLabel; // "2024-01-15", "2024-W03", "2024-01", "14:00"
    private Long commentCount;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
}