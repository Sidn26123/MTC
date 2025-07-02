package com.sidn.metruyenchu.notificationservice.service;

import com.sidn.metruyenchu.notificationservice.dto.request.NotificationRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationResponse;
import com.sidn.metruyenchu.notificationservice.dto.response.ReportResponse;
import com.sidn.metruyenchu.notificationservice.entity.Notification;
import com.sidn.metruyenchu.notificationservice.entity.NotificationPreference;
import com.sidn.metruyenchu.notificationservice.entity.NotificationTemplate;
import com.sidn.metruyenchu.notificationservice.mapper.NotificationMapper;
import com.sidn.metruyenchu.notificationservice.repository.NotificationPreferenceRepository;
import com.sidn.metruyenchu.notificationservice.repository.NotificationRepository;
import com.sidn.metruyenchu.notificationservice.repository.NotificationTemplateRepository;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationPriority;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationType;
import com.sidn.metruyenchu.shared_library.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationService {
    
    
    NotificationRepository notificationRepository;
    
    
    NotificationPreferenceRepository preferenceRepository;
    
    
    NotificationTemplateRepository templateRepository;
    
    
    EmailService emailService;
    
    
    FCMService pushService;
    
    
    WebSocketService webSocketService;
    private final NotificationMapper notificationMapper;

    public Notification createNotification(NotificationRequest dto) {
        NotificationTemplate template = templateRepository.findByNotificationType(dto.getNotificationType().name())
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));
        
        Notification notification = new Notification();
        notification.setRecipientId(dto.getRecipientId());
        notification.setSenderId(dto.getSenderId());
        notification.setNotificationType(dto.getNotificationType());
//        Map<String, String> titleVariables = {
//            "recipientName", getUserName(dto.getRecipientId()),
//            "senderName", getUserName(dto.getSenderId())
//        };
//        notification.setTitle(processTemplate(template.getTitleTemplate(), dto.getVariables()));
//        notification.setContent(processTemplate(template.getContentTemplate(), dto.getVariables()));
//        notification.setActionUrl(processTemplate(template.getActionUrlTemplate(), dto.getVariables()));
        notification.setTitle(dto.getTitle());
        notification.setContent(dto.getContent());
        notification.setActionUrl(dto.getActionUrl());
        notification.setMetadata(dto.getMetadata());
        notification.setPriority(dto.getPriority() != null ? dto.getPriority() : template.getDefaultPriority());
        notification.setScheduledAt(dto.getScheduledAt());
        Duration expiresAt = Duration.parse("PT72H");
        if (dto.getExpiresAt() != null) {
            notification.setExpiresAt(LocalDateTime.now().plus(expiresAt));
        }
        
        Notification saved = notificationRepository.save(notification);
        
        // Send immediately if not scheduled
        if (dto.getScheduledAt() == null) {
            sendNotification(saved);
        }
        
        return saved;
    }
    
    void sendNotification(Notification notification) {
        NotificationPreference preference = getOrCreatePreference(notification.getRecipientId());
        
        if (!shouldSendNotification(notification, preference)) {
            return;
        }
        
        // Send in-app notification
        sendInAppNotification(notification);
        
//        // Send email if enabled
//        if (shouldSendEmail(notification.getNotificationType(), preference)) {
//            sendEmailNotification(notification);
//        }
//
//        // Send push if enabled
//        if (shouldSendPush(notification.getNotificationType(), preference)) {
//            sendPushNotification(notification);
//        }
        
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }
    
    void sendInAppNotification(Notification notification) {
        // Send via WebSocket for real-time notification
        NotificationResponse response = notificationMapper.toResponse(notification);
        webSocketService.sendToUser(notification.getRecipientId(), 
                                   "notification",
                response);
    }
    
    boolean shouldSendNotification(Notification notification, NotificationPreference preference) {
        // Check if in quiet hours
        if (isInQuietHours(preference)) {
            // Only send urgent notifications during quiet hours
            return notification.getPriority() == NotificationPriority.URGENT;
        }
        
        // Check type-specific preferences
        return isNotificationTypeEnabled(notification.getNotificationType(), preference);
    }

    private boolean isNotificationTypeEnabled(NotificationType notificationType, NotificationPreference preference) {
        return true;

    }

    private boolean isInQuietHours(NotificationPreference preference) {
        if (preference.getQuietHoursStart() == null || preference.getQuietHoursEnd() == null) {
            return false; // No quiet hours set
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime quietStart = LocalDateTime.of(now.toLocalDate(), preference.getQuietHoursStart());
        LocalDateTime quietEnd = LocalDateTime.of(now.toLocalDate(), preference.getQuietHoursEnd());

        return now.isAfter(quietStart) && now.isBefore(quietEnd);
    }

    // Story interaction notifications
    public void notifyStoryLiked(String storyId, String likerId, String publisherId) {
        NotificationRequest dto = NotificationRequest.builder()
                .recipientId(publisherId)
                .senderId(likerId)
                .notificationType(NotificationType.STORY_LIKED)
                .priority(NotificationPriority.LOW)
                .metadata(Map.of("storyId", storyId, "likerId", likerId))
//                .variables(Map.of(
//                    "likerName", getUserName(likerId),
//                    "storyTitle", getStoryTitle(storyId)
//                ))
                .build();
        
        createNotification(dto);
    }
    
    public void notifyStoryFollowed(String storyId, String followerId, String publisherId) {
        NotificationRequest dto = NotificationRequest.builder()
                .recipientId(publisherId)
                .senderId(followerId)
                .notificationType(NotificationType.STORY_FOLLOWED)
                .priority(NotificationPriority.LOW)
                .metadata(Map.of("storyId", storyId, "followerId", followerId))
//                .variables(Map.of(
//                    "followerName", getUserName(followerId),
//                    "storyTitle", getStoryTitle(storyId)
//                ))
                .build();
        
        createNotification(dto);
    }
    
    public void notifyStoryCommented(String storyId, String commenterId, String publisherId, String commentContent) {
        NotificationRequest dto = NotificationRequest.builder()
                .recipientId(publisherId)
                .senderId(commenterId)
                .notificationType(NotificationType.STORY_COMMENTED)
                .priority(NotificationPriority.MEDIUM)
                .metadata(Map.of("storyId", storyId, "commenterId", commenterId))
//                .variables(Map.of(
//                    "commenterName", getUserName(commenterId),
//                    "storyTitle", getStoryTitle(storyId),
//                    "commentPreview", truncateText(commentContent, 50)
//                ))
                .build();
        
        createNotification(dto);
    }
    
    public void notifyStoryRated(String storyId, String raterId, String publisherId, int rating) {
        NotificationRequest dto = NotificationRequest.builder()
                .recipientId(publisherId)
                .senderId(raterId)
                .notificationType(NotificationType.STORY_RATED)
                .priority(NotificationPriority.LOW)
                .metadata(Map.of("storyId", storyId, "raterId", raterId, "rating", rating))
//                .variables(Map.of(
//                    "raterName", getUserName(raterId),
//                    "storyTitle", getStoryTitle(storyId),
//                    "rating", String.valueOf(rating)
//                ))
                .build();
        
        createNotification(dto);
    }
    
    // Report notifications
    public void notifyStoryReported(String storyId, String reporterId, String publisherId, String reportReason) {
        NotificationRequest dto = NotificationRequest.builder()
                .recipientId(publisherId)
                .senderId(reporterId)
                .notificationType(NotificationType.STORY_REPORTED)
                .priority(NotificationPriority.HIGH)
                .metadata(Map.of("storyId", storyId, "reporterId", reporterId))
//                .variables(Map.of(
//                    "storyTitle", getStoryTitle(storyId),
//                    "reportReason", reportReason
//                ))
                .build();
        
        createNotification(dto);
    }
    
    public void notifyReportAssignment(String assigneeId, ReportResponse report) {
        NotificationRequest dto = NotificationRequest.builder()
                .recipientId(assigneeId)
                .notificationType(NotificationType.REPORT_ASSIGNED)
                .priority(NotificationPriority.MEDIUM)
                .metadata(Map.of("reportId", report.getId()))
//                .variables(Map.of(
//                    "reportTitle", report.getTitle(),
//                    "reportType", report.getReportType().getDisplayName()
//                ))
                .build();
        
        createNotification(dto);
    }
    
    public void notifyReportStatusChange(String reporterId, ReportResponse report) {
        NotificationRequest dto = NotificationRequest.builder()
                .recipientId(reporterId)
                .notificationType(NotificationType.REPORT_STATUS_CHANGED)
                .priority(NotificationPriority.MEDIUM)
                .metadata(Map.of("reportId", report.getId()))
//                .variables(Map.of(
//                    "reportTitle", report.getTitle(),
//                    "newStatus", report.getStatus().getDisplayName()
//                ))
                .build();
        
        createNotification(dto);
    }
    
    // User activity notifications
//    public void notifyNewChapter(String storyId, String publisherId) {
//        // Get all followers of the story
//        List<String> followers = getStoryFollowers(storyId);
//
//        for (String followerId : followers) {
//            NotificationRequest dto = NotificationRequest.builder()
//                    .recipientId(followerId)
//                    .senderId(publisherId)
//                    .notificationType(NotificationType.NEW_CHAPTER)
//                    .priority(NotificationPriority.MEDIUM)
//                    .metadata(Map.of("storyId", storyId))
////                    .variables(Map.of(
////                        "storyTitle", getStoryTitle(storyId),
////                        "authorName", getUserName(publisherId)
////                    ))
//                    .build();
//
//            createNotification(dto);
//        }
//    }
//
//    public void notifyNewStoryFromAuthor(String authorId, String storyId) {
//        // Get all followers of the author
//        List<String> followers = getAuthorFollowers(authorId);
//
//        for (String followerId : followers) {
//            NotificationRequest dto = NotificationRequest.builder()
//                    .recipientId(followerId)
//                    .senderId(authorId)
//                    .notificationType(NotificationType.NEW_STORY_FROM_AUTHOR)
//                    .priority(NotificationPriority.MEDIUM)
//                    .metadata(Map.of("storyId", storyId, "authorId", authorId))
////                    .variables(Map.of(
////                        "authorName", getUserName(authorId),
////                        "storyTitle", getStoryTitle(storyId)
////                    ))
//                    .build();
//
//            createNotification(dto);
//        }
//    }
    
    // Batch notifications
    public void sendWeeklyStats(String publisherId, Map<String, Object> stats) {
        NotificationRequest dto = NotificationRequest.builder()
                .recipientId(publisherId)
                .notificationType(NotificationType.WEEKLY_STATS)
                .priority(NotificationPriority.LOW)
                .metadata(stats)
//                .variables(stats)
                .build();
        
        createNotification(dto);
    }
    
    // Query methods
    public PageResponse<NotificationResponse> getNotificationsForUser(String userId, boolean unreadOnly, Pageable pageable) {
        if (unreadOnly) {
            return PageUtils.toPageResponse(notificationRepository.findByRecipientIdAndIsReadFalseAndIsArchivedFalse(userId, pageable)
                    , notificationMapper::toResponse,
                    pageable.getPageNumber()+1);
        }
//        return notificationRepository.findByRecipientIdAndIsArchivedFalse(userId, pageable);
        return PageUtils.toPageResponse(notificationRepository.findByRecipientIdAndIsArchivedFalse(userId, pageable)
                , notificationMapper::toResponse,
                pageable.getPageNumber()+1);
    }
    
    public long getUnreadCount(String userId) {
        return notificationRepository.countByRecipientIdAndIsReadFalse(userId);
    }
    
    public void markAsRead(String notificationId, String userId) {
        Notification notification = notificationRepository.findByIdAndRecipientId(notificationId, userId)
                .orElseThrow(() -> new AppException(
                        ErrorCode.NOTIFICATION_NOT_FOUND));
        
        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }
    
    public void markAllAsRead(String userId) {
        List<Notification> unreadNotifications = notificationRepository
                .findByRecipientIdAndIsReadFalse(userId);
        
        LocalDateTime now = LocalDateTime.now();
        unreadNotifications.forEach(notification -> {
            notification.setIsRead(true);
            notification.setReadAt(now);
        });
        
        notificationRepository.saveAll(unreadNotifications);
    }
    
    public void archiveNotification(String notificationId, String userId) {
        Notification notification = notificationRepository.findByIdAndRecipientId(notificationId, userId)
                .orElseThrow(() ->new AppException(
                        ErrorCode.NOTIFICATION_NOT_FOUND));
        
        notification.setIsArchived(true);
        notificationRepository.save(notification);
    }
    
    // Helper methods
    String processTemplate(String template, Map<String, String> variables) {
        if (template == null || variables == null) {
            return template;
        }
        
        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }
    
    NotificationPreference getOrCreatePreference(String userId) {
        return preferenceRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultPreference(userId));
    }
    
    NotificationPreference createDefaultPreference(String userId) {
        NotificationPreference preference = new NotificationPreference();
        preference.setUserId(userId);
        return preferenceRepository.save(preference);
    }
}