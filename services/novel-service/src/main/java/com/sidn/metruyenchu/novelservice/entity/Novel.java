package com.sidn.metruyenchu.novelservice.entity;

import com.sidn.metruyenchu.novelservice.enums.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Truyện
 */
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

    String publisherNote;

    @Column(nullable = false)
    String currentPublisher;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    NovelState novelState = NovelState.CREATED; //trạng thái ban đầu

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    NovelAttribute novelAttribute = NovelAttribute.FREE; //thuộc tính truyện

    @Builder.Default
    @Column(columnDefinition = "FLOAT DEFAULT 0.0")
    Float avgRate = 0.0f;

    @Builder.Default
    @Column(columnDefinition = "INTEGER DEFAULT 0")
    Integer totalRates = 0;

    @Builder.Default
    @Column(columnDefinition = "INTEGER DEFAULT 0")
    Integer totalComments = 0;

    @Builder.Default
    @Column(columnDefinition = "INTEGER DEFAULT 0")
    Integer totalChapters = 0;

    @Builder.Default
    @Column(columnDefinition = "INTEGER DEFAULT 0")
    Integer totalViews = 0;

    @Builder.Default
    @Column(columnDefinition = "INTEGER DEFAULT 0")
    Integer totalPromotions = 0;

    @Builder.Default
    @Column(columnDefinition = "INTEGER DEFAULT 0")
    Integer totalBookmarks = 0;



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
//    @ManyToOne
//    @JoinColumn(name = "novel_status_id")
//    NovelStatus novelStatus;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<NovelSect> novelSects;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<NovelWorldScene> novelWorldScenes;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<NovelMainCharacterTrait> novelMainCharacterTraits;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
