package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import com.sidn.metruyenchu.feedbackservice.validator.EnumValue;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportCreationRequest {
    String content;
    String reportedBy;
//    String reportInNovelId;
    String lastReadChapterId;
    FeedbackType reportEntityType;
    String reportEntityId;
    ReportStatus status = ReportStatus.PENDING;


}
