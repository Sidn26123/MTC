package com.sidn.metruyenchu.notificationservice.dto.response;

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
public class NotificationTemplateResponse {
    String id;
    String notificationType;
    String titleTemplate;
    String contentTemplate;
    String emailTemplate;
    String pushTemplate;
    String actionUrlTemplate;
    NotificationPriority defaultPriority;
    Boolean isActive;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}