package com.sidn.metruyenchu.user_service.entity;

import com.sidn.metruyenchu.user_service.enums.AccountStatusEnums;
import com.sidn.metruyenchu.user_service.enums.GenderEnums;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String username;

    String userId;

    @Column(nullable = true)
    String email;

    @Column(nullable = false)
    String avatarPath;

    AccountStatusEnums status;

    GenderEnums gender;

    @Builder.Default
    Integer level = 1;
    @Builder.Default
    Integer levelExperiencePoint = 0;

    @CreationTimestamp
    @Column(nullable = false)
    String createdAt;

    @UpdateTimestamp
    String updatedAt;
    String deletedAt;

    @Column(nullable = false)
    String firstName;
    String lastName;
    @Column(nullable = false)
    Date dateOfBirth;

    @Builder.Default
    Integer readNovelCount = 0;
    @Builder.Default
    Integer commentedCount = 0;
    @Builder.Default
    Integer readChapterCount = 0;
    @Builder.Default
    Integer exportedNovelCount = 0;
    @Builder.Default
    Integer markedNovelCount = 0;
    @Builder.Default
    Integer recommendedNovelCount = 0;
    @Builder.Default
    Integer ratedCount = 0;
    @Builder.Default
    Float totalSpentPoint = 0.0f;
    @Builder.Default
    Integer totalChapterBought = 0;
    @Builder.Default
    boolean isDeleted = false;
    @Builder.Default
    boolean isEnabled = false;
}
