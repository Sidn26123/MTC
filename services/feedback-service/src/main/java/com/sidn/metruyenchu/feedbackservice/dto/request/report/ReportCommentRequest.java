package com.sidn.metruyenchu.feedbackservice.dto.request.report;
import com.sidn.metruyenchu.shared_library.enums.feedback.CommenterRole;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportCommentRequest {
    String reportId;
    String commenterId;
    CommenterRole commenterRole;
    String content;
    Boolean isInternal;
}