package com.sidn.metruyenchu.novelservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reading_logs", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_novel_id", columnList = "novel_id"),
        @Index(name = "idx_read_at", columnList = "read_at"),
        @Index(name = "idx_user_novel", columnList = "user_id, novel_id"),
        @Index(name = "idx_user_novel_read_at", columnList = "user_id, novel_id, read_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReadingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    String id;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "novel_id", nullable = false)
    String novelId;

    @Column(name = "chapter_id", nullable = false)
    String chapterId;

    @Column(name = "read_at", nullable = false)
    @CreationTimestamp
    LocalDateTime readAt;

    @Column(name = "end_at")
    LocalDateTime endAt;

    @Column(name = "duration")
    Integer duration; // thời gian đọc chương, đơn vị giây

    @Column(name = "progress")
    Float progress; // Tiến độ đọc, giá trị từ 0.0 đến 1.0

    @Column(name = "device")
    String device;

    @Column(name = "ip_address")
    String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    String userAgent;

    @Column(name = "is_finished")
    Boolean isFinished;

    @PrePersist
    protected void onCreate() {
        if (readAt == null) {
            readAt = LocalDateTime.now();
        }
    }
}