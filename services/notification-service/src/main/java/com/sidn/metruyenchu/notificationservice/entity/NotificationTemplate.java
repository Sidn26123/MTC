package com.sidn.metruyenchu.notificationservice.entity;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationPriority;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "notification_templates")
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "notification_type", nullable = false, unique = true)
    String notificationType;

    @Column(name = "title_template", nullable = false)
    String titleTemplate;

    @Column(name = "content_template", nullable = false, columnDefinition = "TEXT")
    String contentTemplate;

    @Column(name = "email_template", columnDefinition = "TEXT")
    String emailTemplate;

    @Column(name = "push_template")
    String pushTemplate;

    @Column(name = "action_url_template", length = 500)
    String actionUrlTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_priority")
    NotificationPriority defaultPriority;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
