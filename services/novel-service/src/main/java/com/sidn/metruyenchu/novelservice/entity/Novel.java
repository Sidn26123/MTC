package com.sidn.metruyenchu.novelservice.entity;

import com.sidn.metruyenchu.novelservice.enums.NovelType;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import com.sidn.metruyenchu.novelservice.enums.ProgressStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    String currentPublisher;

    @Column(nullable = false, length = 256)
    String novelCoverImage;

    @Column(columnDefinition = "TEXT")
    String description;

    @Builder.Default
    Integer chapterReadToComment = 0;

    @Builder.Default
    Integer chapterReadToRate = 10;

    Integer fullSetPurchaseDiscount;

    Integer wordCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NovelType novelType; //loại truyện: dịch, sáng tác

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    NovelVisibility novelVisibility = NovelVisibility.PRIVATE; //khả năng truy cập

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    ProgressStatus progressStatus = ProgressStatus.IN_PROGRESS;

    @Builder.Default
    @Column(columnDefinition = "FLOAT DEFAULT 0.0")
    Float avgRate = 0.0f;

    @Builder.Default
    Integer totalRates = 0;

    @Builder.Default
    Integer totalComments = 0;

    @ManyToOne
    @JoinColumn(name = "author_id")
    NovelAuthor author;

//    @JoinTable(
//            name = "novel_novelstatus",
//            joinColumns = @JoinColumn(name = "novel_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "novel_status_id", referencedColumnName = "id")
//    )


    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<NovelStatusDetail> status = new ArrayList<>();

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    List<NovelSect> novelSects;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    List<NovelWorldScene> novelWorldScenes;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    List<NovelMainCharacterTrait> novelMainCharacterTraits;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    List<NovelGenre> novelGenres;


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
