package com.sidn.metruyenchu.novelservice.entity;

import com.sidn.metruyenchu.novelservice.enums.ChapterState;
import com.sidn.metruyenchu.novelservice.enums.DraftState;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Draft {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    // Tiêu đề bản nháp
    @Column(length = 256)
    String title;

    // Nội dung bản nháp
//    @Lob
    @Column(columnDefinition = "TEXT")
    String content;

    // Ghi chú cá nhân (tuỳ chọn)
    @Column(length = 512)
    String note;

    // Người tạo bản nháp
    @Column(nullable = false)
    String publisher;

    // Có thể gắn với 1 truyện hoặc không
    @ManyToOne
    @JoinColumn(name = "novel_id")
    Novel novel;

    // Có thể gắn với chapter cụ thể hoặc không
    @ManyToOne
    @JoinColumn(name = "chapter_id")
    Chapter chapter;

    // Trạng thái nháp (draft, submitted, archived)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    DraftState state = DraftState.DRAFT;

    // Cờ đánh dấu nháp đã được chuyển thành chapter
    @Builder.Default
    Boolean isConvertedToChapter = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;
}
