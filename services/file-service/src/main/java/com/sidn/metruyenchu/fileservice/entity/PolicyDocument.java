package com.sidn.metruyenchu.fileservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PolicyDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, unique = true, length = 100)
    String slug;

    @Column(nullable = false, length = 256)
    String title;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    @Builder.Default
    Boolean isPublic = true;

    @Builder.Default
    Integer version = 1;

    String updatedBy;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @CreationTimestamp
    LocalDateTime createdAt;

    @Builder.Default
    boolean isDeleted = false;
}
