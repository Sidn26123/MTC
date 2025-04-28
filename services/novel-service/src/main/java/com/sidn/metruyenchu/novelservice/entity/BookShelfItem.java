package com.sidn.metruyenchu.novelservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Bảng chi tiết các item trong bookshelf, mỗi item là 1 novel được đọc
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"bookshelf_id", "novel_id"})
)
public class BookShelfItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id")
    BookShelf bookShelf;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    Novel novel;

    Integer currentChapterIdx;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;

    ///Nếu noticed thì sẽ có thông báo tới user khi truyện có chương mới, truyeện đổi status như: hoàn thành, drop ...
    @Builder.Default
    Boolean isNoticed = false;
}
