package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import com.sidn.metruyenchu.feedbackservice.enums.TargetType;
import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportType;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportCreationRequest {

    String content;

    @NonNull
    String reporterId;

    @NonNull
    ReportType reportType;

    @NonNull
    TargetType targetType;

    @NonNull
    String targetId;

    @NonNull
    String reportEntityId;

    @NonNull
    String title;

    @NonNull
    String description;

    List<String> evidenceUrls;

    Priority priority;


    String assignedTo;

    AssigneeRole assignedRole;

    String resolutionNote;
}