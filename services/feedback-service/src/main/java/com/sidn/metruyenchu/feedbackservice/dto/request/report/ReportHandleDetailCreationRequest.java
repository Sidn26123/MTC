package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import com.sidn.metruyenchu.feedbackservice.enums.ReportHandleStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportHandleDetailCreationRequest {
    String reportId;
    String handleBy;
    String handlerNote;
    String description;
    ReportHandleStatus status;
}
