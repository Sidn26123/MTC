package com.sidn.metruyenchu.notificationservice.dto.response;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.notification.DeliveryMethod;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationPriority;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    String id;
    String recipientId;
    String senderId;
    NotificationType notificationType;
    String title;
    String content;
    String actionUrl;
    Map<String, Object> metadata;
    NotificationPriority priority;
    Boolean isRead;
    Boolean isArchived;
    DeliveryMethod deliveryMethod;
    LocalDateTime scheduledAt;
    LocalDateTime sentAt;
    LocalDateTime readAt;
    LocalDateTime expiresAt;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
