package com.sidn.metruyenchu.novelservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


/**
 * Entity lưu các truyện được đánh dấu.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class MarkedNovel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    /**
     * ID của novel mà user bookmark.
     * Tham chiếu tới Novel service.
     */
    @NonNull
    String novelId;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    Novel novel;

    /// Idx của chapter được truy cập gần nhất của truyện được bookark tại thời điểm bookmark. (Nếu chưa đọc thì chapter = 0; nếu bookmark ở ngoài sẽ lấy theo idx chapter của truyện)
    Integer chapter;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;
}
