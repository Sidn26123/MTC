package com.sidn.metruyenchu.novelservice.entity;
import com.sidn.metruyenchu.novelservice.enums.ChapterState;
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

    @Column(nullable = false)
    @Builder.Default
    Long viewCount = 0L;

    @Column(nullable = false)
    String publisher;

    @Column(nullable = false)
    Integer chapterIdx;

    @Builder.Default
    Integer amountToUnlock = 0;

    String content;

    Integer totalComments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    ChapterState state = ChapterState.CREATED;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    List<ChapterStatusDetail> chapterStatus;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    Novel novel;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;

    @Builder.Default
    Boolean isActive = true;
}
