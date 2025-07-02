package com.sidn.metruyenchu.user_service.dto.request.UserProfile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterTokenRequest {
    private String userId;
    private String token;
    
}