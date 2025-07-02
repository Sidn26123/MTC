package com.sidn.metruyenchu.notificationservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationUpdateRequest {
    Boolean isRead;
    Boolean isArchived;
    LocalDateTime readAt;
    LocalDateTime sentAt;
    LocalDateTime expiresAt;
}
