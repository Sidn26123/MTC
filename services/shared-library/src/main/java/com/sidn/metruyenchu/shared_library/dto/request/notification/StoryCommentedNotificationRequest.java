package com.sidn.metruyenchu.shared_library.dto.request.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryCommentedNotificationRequest {
    private String novelId;
    private String commenterId;
    private String publisherId;
    private String commentContent;
}