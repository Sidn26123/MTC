package com.sidn.metruyenchu.feedbackservice.dto.response.report;
import com.sidn.metruyenchu.shared_library.enums.feedback.ActorRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportActionType;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportActionResponse {
    String id;
    String reportId;
    String actorId;
    ActorRole actorRole;
    ReportActionType actionType;
    String oldValue;
    String newValue;
    String note;
    LocalDateTime createdAt;
}
