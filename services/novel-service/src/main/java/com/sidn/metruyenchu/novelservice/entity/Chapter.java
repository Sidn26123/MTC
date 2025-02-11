package com.sidn.metruyenchu.novelservice.entity;
import com.sidn.metruyenchu.novelservice.enums.ChapterStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, length = 256)
    String name;

//    @Lob
    String content;

    @NonNull
    String publisher;

    @Column(nullable = false)
    Integer chapterIdx;

    Integer amountToUnlock = 0;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ChapterStatusEnum status = ChapterStatusEnum.CREATED;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    Set<ChapterStatusDetail> chapterStatus;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    Novel novel;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    Set<NovelSect> novelSects;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    Set<NovelWorldScene> novelWorldScenes;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    Set<NovelMainCharTrait> novelMainCharTraits;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    Set<NovelGenre> novelGenres;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    Boolean isDeleted = false;

    @Builder.Default
    Boolean isActive = true;
}
