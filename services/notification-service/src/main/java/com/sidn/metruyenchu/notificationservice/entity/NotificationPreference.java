package com.sidn.metruyenchu.notificationservice.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    @Column(name = "user_id", unique = true, nullable = false)
    String userId;
    
    // Story interactions
    @Column(name = "story_interactions_enabled")
    Boolean storyInteractionsEnabled = true;
    
    @Column(name = "story_interactions_email")
    Boolean storyInteractionsEmail = false;
    
    @Column(name = "story_interactions_push")
    Boolean storyInteractionsPush = true;
    
    // Publisher activities
    @Column(name = "publisher_activities_enabled")
    Boolean publisherActivitiesEnabled = true;
    
    @Column(name = "publisher_activities_email")
    Boolean publisherActivitiesEmail = true;
    
    @Column(name = "publisher_activities_push")
    Boolean publisherActivitiesPush = true;
    
    // System notifications
    @Column(name = "system_notifications_enabled")
    Boolean systemNotificationsEnabled = true;
    
    @Column(name = "system_notifications_email")
    Boolean systemNotificationsEmail = false;
    
    @Column(name = "system_notifications_push")
    Boolean systemNotificationsPush = true;
    
    // User activities
    @Column(name = "user_activities_enabled")
    Boolean userActivitiesEnabled = true;
    
    @Column(name = "user_activities_email")
    Boolean userActivitiesEmail = false;
    
    @Column(name = "user_activities_push")
    Boolean userActivitiesPush = true;
    
    // Quiet hours
    @Column(name = "quiet_hours_start")
    LocalTime quietHoursStart = LocalTime.of(22, 0);
    
    @Column(name = "quiet_hours_end")
    LocalTime quietHoursEnd = LocalTime.of(7, 0);
    
    String timezone = "Asia/Ho_Chi_Minh";
    
    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    
    // Constructors, getters, setters
}