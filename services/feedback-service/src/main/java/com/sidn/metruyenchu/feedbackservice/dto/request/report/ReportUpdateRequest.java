package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportActionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportUpdateRequest {

    String title;

    String description;

    List<String> evidenceUrls;

    ReportActionType actionType;

    ReportStatus status;

    Priority priority;

    String assignedTo;

    AssigneeRole assignedRole;

    String resolutionNote;

    Boolean isClosed;

    Boolean isDeleted;

    LocalDateTime resolvedAt;
}

