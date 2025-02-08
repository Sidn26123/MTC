package com.sidn.metruyenchu.novelservice.entity;
import com.sidn.metruyenchu.novelservice.enums.ChapterStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Lob
    String content;

    @NonNull
    String publisher;

    @Column(nullable = false)
    Integer chapterIdx;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ChapterStatus status = ChapterStatus.CREATED;

    Integer amountToUnlock = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
    Boolean isDeleted = false;

    @Builder.Default
    Boolean isActive = true;
}
