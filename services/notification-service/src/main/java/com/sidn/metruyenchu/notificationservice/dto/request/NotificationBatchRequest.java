package com.sidn.metruyenchu.notificationservice.dto.request;
import com.sidn.metruyenchu.shared_library.enums.notification.BatchStatus;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationBatchRequest {
    String batchName;
    String notificationType;
    Integer totalRecipients;
    BatchStatus status;
    LocalDateTime scheduledAt;
}
