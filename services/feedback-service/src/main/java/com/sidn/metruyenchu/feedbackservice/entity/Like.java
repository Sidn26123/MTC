package com.sidn.metruyenchu.feedbackservice.entity;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.descriptor.jdbc.TinyIntJdbcType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"liker", "targetId", "targetType"})
})
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String liker;

    @Column(nullable = false)
    String targetId;

    @Enumerated(EnumType.STRING)
    FeedbackType targetType;

    @Builder.Default
    @Column(nullable = false)
    Boolean isLiked = true;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;
}
