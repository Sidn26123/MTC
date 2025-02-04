package com.sidn.metruyenchu.novelservice.entity;

import com.sidn.metruyenchu.novelservice.enums.NovelStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
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
public class Novel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, unique = true, length = 256)
    String name;

    String displayName;

    @Column(length = 256)
    String originName;

    @Column(columnDefinition = "TEXT")
    String originLink;

    Integer totalChapters;

    @ManyToOne
    @JoinColumn(name = "author_id")
    NovelAuthor author;

    String currentPublisher;

    @Column(nullable = false, length = 256)
    String novelImage;

    @Column(columnDefinition = "TEXT")
    String description;

    @ManyToOne
    NovelStatusModerationStatus moderationStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NovelType novelType;

    @Enumerated(EnumType.STRING)
    NovelVisibility novelVisibility = NovelVisibility.PRIVATE;

    Float avgRate;
    Integer totalRates = 0;
    Integer totalComments = 0;
    Boolean isDeleted = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

}
