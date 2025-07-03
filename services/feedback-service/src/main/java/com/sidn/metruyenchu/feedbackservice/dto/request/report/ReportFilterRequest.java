package com.sidn.metruyenchu.feedbackservice.dto.request.report;

import com.sidn.metruyenchu.feedbackservice.enums.ReportHandleStatus;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportStatus;
import com.sidn.metruyenchu.feedbackservice.enums.TargetType;
import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class ReportFilterRequest {
//    ReportStatus status;
//    LocalDateTime fromTime;
//    LocalDateTime toTime;
//    String reportOfNovel;
//    String reportOfRating;
//    String reportOfComment;
//    boolean isDeleted;
//    boolean isClosed;
//}

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportFilterRequest {
    String reporterId;
    ReportType reportType;
    TargetType targetType;
    String targetId;
    String title;
    Priority priority;
    ReportStatus status;
    String assignedTo;
    AssigneeRole assignedRole;
    Boolean isClosed;
    Boolean isDeleted;

    LocalDateTime createdAfter;
    LocalDateTime createdBefore;

    LocalDateTime resolvedAfter;
    LocalDateTime resolvedBefore;
}
