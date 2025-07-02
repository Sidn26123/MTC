package com.sidn.metruyenchu.user_service.dto.response.UserProfile;

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
public class UserProfileResponse {
    String id;
    String username;
    String email;
    String avatarPath;
    String userId;
    AccountStatusEnums status;
    GenderEnums gender;
    String firstName;
    String lastName;
    Date dateOfBirth;
    Integer readNovelCount = 0;
    Integer commentedCount = 0;
    Integer readChapterCount = 0;
    Integer exportedNovelCount = 0;
    Integer markedNovelCount = 0;
    Integer recommendedNovelCount = 0;
    Integer ratedCount = 0;
    Float totalSpentPoint = 0.0f;
    Integer totalChapterBought = 0;
    Integer level;
    Integer levelExperiencePoint;
    boolean isDeleted = false;
    boolean isEnabled = false;
}
