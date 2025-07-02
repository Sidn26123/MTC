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
public class UserProfileUpdateRequest {
    String email;
    String avatarPath;
    AccountStatusEnums status;
    GenderEnums gender;
    String firstName;
    String lastName;
    Date dateOfBirth;
    String userId;
    Integer readNovelCount ;
    Integer commentedCount ;
    Integer readChapterCount ;
    Integer exportedNovelCount ;
    Integer markedNovelCount ;
    Integer recommendedNovelCount ;
    Integer ratedCount ;
    Integer level;
    Integer levelExperiencePoint;
    Float totalSpentPoint;
    Integer totalChapterBought ;
    Boolean isDeleted;
    Boolean isEnabled;
}
