package com.sidn.metruyenchu.shared_library.dto.request.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryLikedNotificationRequest {
    private String storyId;
    private String likerId;
    private String publisherId;
}