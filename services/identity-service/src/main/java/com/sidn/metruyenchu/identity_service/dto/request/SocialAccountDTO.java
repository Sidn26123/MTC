package com.sidn.metruyenchu.identity_service.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SocialAccountDTO {


    @JsonProperty("google_id")
    protected String googleId;


    public boolean isGoogleAccountIdValid() {
        return googleId != null && !googleId.isEmpty();
    }

    // Phương thức kiểm tra xem người dùng có phải là người dùng đăng nhập xã hội hay không
    public boolean isSocialLogin() {
        return (googleId != null && !googleId.isEmpty());

    }
}
