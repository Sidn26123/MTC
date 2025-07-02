package com.sidn.metruyenchu.user_service.dto.request.UserProfile;

import com.sidn.metruyenchu.user_service.enums.AccountStatusEnums;
import com.sidn.metruyenchu.user_service.enums.GenderEnums;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationRequest {
    String username;
    String email;
    String avatarPath;
    String userId;
    AccountStatusEnums status;
    GenderEnums gender;
    String firstName;
    String lastName;
    Date dateOfBirth;
}
