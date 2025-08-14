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
public class AuthorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String displayName;

    String avatarPath;

    Integer publishedNovel;

    @CreationTimestamp
    @Column(nullable = false)
    String createdAt;

    @UpdateTimestamp
    String updatedAt;
    String deletedAt;

    String firstName;
    String lastName;
    @Column(nullable = false)
    Date dateOfBirth;
}
