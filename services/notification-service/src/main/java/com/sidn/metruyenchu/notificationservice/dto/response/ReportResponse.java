package com.sidn.metruyenchu.notificationservice.dto.response;


import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {

    String id;
    String title;
    String description;
    String content;

    String reporterId;              // Người tạo báo cáo
    ReportType reportType;          // Loại báo cáo
//    TargetType targetType;          // Mục tiêu bị báo cáo
    String targetId;                // ID của đối tượng bị báo cáo

//    FeedbackType reportEntityType;  // Loại thực thể gốc được báo cáo
    String reportEntityId;          // ID thực thể gốc

    List<String> evidenceUrls;      // Các bằng chứng
    Priority priority;              // Độ ưu tiên
//    ReportStatus status;            // Trạng thái

    String assignedTo;              // Người được giao xử lý
    AssigneeRole assignedRole;          // Vai trò người xử lý
    String resolutionNote;          // Ghi chú xử lý

    boolean isDeleted;
    boolean isClosed;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;
}

