package com.sidn.metruyenchu.user_service.dto.request.UserProfile;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    private String title;
    private String body;
    private List<String> userIds;
    private String topic;
    private String token;
    private Map<String, String> data;
}