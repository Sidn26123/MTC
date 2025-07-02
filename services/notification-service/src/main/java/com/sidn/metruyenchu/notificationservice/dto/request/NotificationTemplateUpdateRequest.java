package com.sidn.metruyenchu.notificationservice.dto.request;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplateUpdateRequest {
    String titleTemplate;
    String contentTemplate;
    String emailTemplate;
    String pushTemplate;
    String actionUrlTemplate;
    NotificationPriority defaultPriority;
    Boolean isActive;
}