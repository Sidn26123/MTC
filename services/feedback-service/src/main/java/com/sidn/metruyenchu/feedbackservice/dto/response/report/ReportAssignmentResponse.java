package com.sidn.metruyenchu.feedbackservice.dto.response.report;

import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportAssignmentResponse {
    String id;
    String reportId;
    String assigneeId;
    AssigneeRole assigneeRole;
    Boolean isPrimary;
    LocalDateTime assignedAt;
}
