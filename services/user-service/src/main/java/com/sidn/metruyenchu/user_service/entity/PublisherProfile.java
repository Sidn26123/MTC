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
public class PublisherProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, unique = true)
    String userId;

    @Column(nullable = false)
    String displayName;

    @Column(nullable = true)
    String avatarPath;

    @Column(nullable = true)
    String bio;

    @Column(nullable = false)
    boolean isVerified;

    @Builder.Default
    Integer publishedNovelCount = 0;

    @Builder.Default
    Integer totalChapterPublished = 0;

    @Builder.Default
    Integer totalViewCount = 0;

    @Builder.Default
    Integer totalFollowerCount = 0;

    @Builder.Default
    Float averageRating = 0.0f;

    @Builder.Default
    Integer ratingCount = 0;

    @Builder.Default
    boolean isBanned = false;

    @Builder.Default
    boolean isDeleted = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Date createdAt;

    @UpdateTimestamp
    Date updatedAt;
}