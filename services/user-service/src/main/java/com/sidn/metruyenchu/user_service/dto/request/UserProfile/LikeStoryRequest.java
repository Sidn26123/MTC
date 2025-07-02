package com.sidn.metruyenchu.user_service.dto.request.UserProfile;

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