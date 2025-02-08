package com.sidn.metruyenchu.novelservice.entity;

import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = false, unique = true, length = 256)
    String slug;

    @Column(nullable = false, length = 256)
    String displayName;

    @Column(length = 256)
    String originName;

    @Column(columnDefinition = "TEXT")
    String originLink;

    Integer totalChapters;

    @ManyToOne
    @JoinColumn(name = "author_id")
    NovelAuthor author;

    @Column(nullable = false)
    String currentPublisher;

    @Column(nullable = false, length = 256)
    String novelCoverImage;

    @Column(columnDefinition = "TEXT")
    String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "novel_novelstatus",
            joinColumns = @JoinColumn(name = "novel_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "novel_status_id", referencedColumnName = "id")
    )
    Set<NovelStatus> status = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NovelType novelType;

    @Enumerated(EnumType.STRING)
    NovelVisibility novelVisibility = NovelVisibility.PRIVATE;
    @Builder.Default
    Float avgRate = 0.0f;
    @Builder.Default
    Integer totalRates = 0;
    @Builder.Default
    Integer totalComments = 0;
    @Builder.Default
    Boolean isDeleted = false;

    @Builder.Default
    Boolean isActive = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

}
