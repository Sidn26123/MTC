package com.sidn.metruyenchu.notificationservice.entity;
import com.sidn.metruyenchu.shared_library.enums.notification.BatchStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Map;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "notification_batches")
public class NotificationBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "batch_name", nullable = false)
    String batchName;

    @Column(name = "notification_type", nullable = false)
    String notificationType;

    @Column(name = "total_recipients")
    Integer totalRecipients;

    @Column(name = "sent_count")
    Integer sentCount;

    @Column(name = "failed_count")
    Integer failedCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    BatchStatus status;

    LocalDateTime scheduledAt;
    LocalDateTime startedAt;
    LocalDateTime completedAt;
    @CreationTimestamp
    LocalDateTime createdAt;
}
