package com.sidn.metruyenchu.feedbackservice.entity;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String content;

    @Column(nullable = false)
    String commentParentId;

    @Enumerated(EnumType.STRING)
    FeedbackType parentType;

    @Column(nullable = false)
    String commenter;

    String commentInNovelId;

    String commentInChapterId;

    @Builder.Default
    Integer likeCount = 0;

    @Builder.Default
    Integer dislikeCount = 0;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;

    @Builder.Default
    Boolean isHidden = true;
}
