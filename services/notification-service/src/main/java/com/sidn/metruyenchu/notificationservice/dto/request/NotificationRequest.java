package com.sidn.metruyenchu.notificationservice.dto.request;

import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.notification.DeliveryMethod;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationPriority;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    String recipientId;
    String senderId;
    NotificationType notificationType;
    String title;
    String content;
    String actionUrl;
    Map<String, Object> metadata;
    NotificationPriority priority;
    DeliveryMethod deliveryMethod;
    LocalDateTime scheduledAt;
    LocalDateTime expiresAt;
}
