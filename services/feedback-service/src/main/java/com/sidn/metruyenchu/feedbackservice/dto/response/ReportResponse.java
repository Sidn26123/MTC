package com.sidn.metruyenchu.feedbackservice.dto.response;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {
    String id;
    String content;
    String reportedBy;
    String reportInNovelId;
    String lastReadChapterId;
    FeedbackType reportEntityType;
    String reportEntityId;
    ReportStatus status;
    boolean isDeleted;
    boolean isClosed;

    LocalDateTime createdAt;
    LocalDateTime completedAt;
    LocalDateTime deletedAt;
}
