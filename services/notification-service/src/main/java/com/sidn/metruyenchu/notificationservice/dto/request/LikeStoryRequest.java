package com.sidn.metruyenchu.notificationservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeStoryRequest {
    private String storyId;
    private String likerUserId;
    private String authorUserId;
}