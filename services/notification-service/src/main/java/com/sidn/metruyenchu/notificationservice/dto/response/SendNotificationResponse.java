package com.sidn.metruyenchu.notificationservice.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendNotificationResponse {
    String messageId;
    String message;
}
