package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportAssignmentRequest {
    String reportId;
    String assigneeId;
    AssigneeRole assigneeRole;
    Boolean isPrimary;
}