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
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String content;

    @Column(nullable = false)
    String ratedBy;

    String novelId;

    String lastReadChapterId;


    @Column(nullable = false)
    Float rate;

    //Đánh giá về thế giới trong truyện
    @Column
    Float worldBuildingRating;

    String worldBuildingContent;

    //Xây dựng nhân vật
    @Column
    Float characterDevelopmentRating;

    String characterDevelopmentContent;
    //Chiều sâu nội dung
    @Column
    Float narrativeDepthRating;

    String narrativeDepthContent;

    @Builder.Default
    Integer totalLikes = 0;

    @Builder.Default
    Integer totalDislikes = 0;

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
