package com.sidn.metruyenchu.user_service.dto.request.UserProfile;

import com.sidn.metruyenchu.author_admin_service.enums.AccountStatusEnums;
import com.sidn.metruyenchu.user_service.dto.ApiResponse;
import com.sidn.metruyenchu.user_service.dto.PageResponse;
import com.sidn.metruyenchu.user_service.enums.GenderEnums;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    AccountStatusEnums status;
    GenderEnums gender;
    String firstName;
    String lastName;
    Date dateOfBirth;
}
