package com.sidn.metruyenchu.feedbackservice.dto.response.report;
import com.sidn.metruyenchu.shared_library.enums.feedback.CommenterRole;
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
public class ReportCommentResponse {
    String id;
    String reportId;
    String commenterId;
    CommenterRole commenterRole;
    String content;
    Boolean isInternal;
    LocalDateTime createdAt;
}