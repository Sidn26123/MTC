package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import com.sidn.metruyenchu.feedbackservice.enums.ReportHandleStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportHandleDetailUpdateRequest {
    String reportId;
    String handleBy;
    String handlerNote;
    String description;
    ReportHandleStatus status;
    boolean isDeleted;

    LocalDateTime completedAt;
    LocalDateTime deletedAt;
}
