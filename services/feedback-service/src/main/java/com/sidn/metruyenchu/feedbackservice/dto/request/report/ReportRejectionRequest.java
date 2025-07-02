package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRejectionRequest {
    String reportId;
    String rejectedBy;
    String rejectReason;
}
