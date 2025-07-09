package com.sidn.metruyenchu.identity_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class GoogleLoginRequest extends SocialAccountDTO {
    private String googleId; // ID Token từ Google
    @Size(min = 4, message = "INVALID_USERNAME")
    String username;

    @Email
    String email;

    String avatarPath;


    public boolean isGoogleAccountIdValid() {
        return googleId != null && !googleId.isEmpty();
    }

}