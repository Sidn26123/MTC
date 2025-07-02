package com.sidn.metruyenchu.feedbackservice.dto.response;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
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
public class ReportHandleDetailResponse {
    String id;
    String reportId;
    String handleBy;
    String handlerNote;
    String description;
    ReportHandleStatus status;
    boolean isDeleted;

    LocalDateTime createdAt;
    LocalDateTime completedAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;
}
