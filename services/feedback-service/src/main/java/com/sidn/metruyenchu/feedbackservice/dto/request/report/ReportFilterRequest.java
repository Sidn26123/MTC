package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import com.sidn.metruyenchu.feedbackservice.enums.ReportHandleStatus;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportFilterRequest {
    ReportStatus status;
    LocalDateTime fromTime;
    LocalDateTime toTime;
    String reportOfNovel;
    String reportOfRating;
    String reportOfComment;
    boolean isDeleted;
    boolean isClosed;
}
