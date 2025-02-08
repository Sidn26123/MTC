package com.sidn.metruyenchu.feedbackservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String content;

    @Column(nullable = false)
    String reviewer;

    String reviewInNovel;

    Integer lastReadChapter;

    @Column(nullable = false)
    Float rating;

    @Builder.Default
    Integer likeCount = 0;

    @Builder.Default
    Integer dislikeCount = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
    Boolean isDeleted = false;

    @Builder.Default
    Boolean isActive = true;
}
