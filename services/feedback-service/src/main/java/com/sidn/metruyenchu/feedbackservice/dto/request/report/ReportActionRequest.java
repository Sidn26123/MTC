package com.sidn.metruyenchu.feedbackservice.dto.request.report;
import com.sidn.metruyenchu.shared_library.enums.feedback.ActorRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportActionType;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportActionRequest {
    String reportId;
    String actorId;
    ActorRole actorRole;
    ReportActionType actionType;
    String oldValue;
    String newValue;
    String note;
}