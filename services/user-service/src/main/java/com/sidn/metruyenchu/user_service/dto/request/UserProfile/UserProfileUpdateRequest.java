package com.sidn.metruyenchu.user_service.dto.request.UserProfile;

import com.sidn.metruyenchu.author_admin_service.enums.AccountStatusEnums;
import com.sidn.metruyenchu.user_service.enums.GenderEnums;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class UserProfileUpdateRequest {
    String email;
    String avatarPath;
    AccountStatusEnums status;
    GenderEnums gender;
    String firstName;
    String lastName;
    Date dateOfBirth;
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
