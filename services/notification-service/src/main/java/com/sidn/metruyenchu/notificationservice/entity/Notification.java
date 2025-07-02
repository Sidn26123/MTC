package com.sidn.metruyenchu.notificationservice.entity;

import com.sidn.metruyenchu.notificationservice.configuations.JsonConverter;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.notification.DeliveryMethod;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationPriority;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationType;
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
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    @Column(name = "recipient_id", nullable = false)
    String recipientId;
    
    @Column(name = "sender_id")
    String senderId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    NotificationType notificationType;
    
    @Column(nullable = false)
    String title;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    String content;
    
    @Column(name = "action_url")
    String actionUrl;
    
    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "JSON")
    Map<String, Object> metadata;
    
    @Enumerated(EnumType.STRING)
    NotificationPriority priority = NotificationPriority.MEDIUM;
    
    @Column(name = "is_read")
    Boolean isRead = false;
    
    @Column(name = "is_archived")
    Boolean isArchived = false;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method")
    DeliveryMethod deliveryMethod = DeliveryMethod.IN_APP;
    
    @Column(name = "scheduled_at")
    LocalDateTime scheduledAt;
    
    @Column(name = "sent_at")
    LocalDateTime sentAt;
    
    @Column(name = "read_at")
    LocalDateTime readAt;
    
    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    
    @Column(name = "expires_at")
    LocalDateTime expiresAt;
    
    // Constructors, getters, setters
}