package com.sidn.metruyenchu.user_service.entity;

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
//    private String id;
//    private String username;
//    private String email;
//    private String password;
//    private String avatar;
//    private String role;
//    private String status;
//    private String createdAt;
//    private String updatedAt;
//    private String deletedAt;
//    private String createdBy;
//    private String updatedBy;
//    private String deletedBy;
//    private String fullName;
//    private String phoneNumber;
//    private String address;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false)
    String username;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String avatar;

    String status;

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
    Integer commentedCount = 0;
    Integer readChapterCount = 0;
    Integer exportedNovelCount = 0;
    Integer markedNovelCount = 0;
    Integer recommendedNovelCount = 0;
    Integer ratedCount = 0;
    Float totalSpentPoint = 0.0f;
    Integer totalChapterBought = 0;
}
