//package com.sidn.metruyenchu.notificationservice.components;
//
//import com.sidn.metruyenchu.notificationservice.entity.Notification;
//import com.sidn.metruyenchu.notificationservice.repository.NotificationRepository;
//import com.sidn.metruyenchu.notificationservice.service.NotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class NotificationScheduler {
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @Autowired
//    private NotificationRepository notificationRepository;
//
//    @Scheduled(fixedRate = 60000) // Every minute
//    public void processScheduledNotifications() {
//        LocalDateTime now = LocalDateTime.now();
//        List<Notification> scheduledNotifications = notificationRepository
//                .findByScheduledAtBeforeAndSentAtIsNull(now);
//
//        for (Notification notification : scheduledNotifications) {
//            try {
//                notificationService.sendNotification(notification);
//            } catch (Exception e) {
////                log.error("Failed to send scheduled notification: " + notification.getId(), e);
//            }
//        }
//    }
//
//    @Scheduled(cron = "0 0 2 * * *") // Daily at 2 AM
//    public void cleanupExpiredNotifications() {
//        LocalDateTime now = LocalDateTime.now();
//        notificationRepository.deleteByExpiresAtBefore(now);
//    }
//
//    @Scheduled(cron = "0 0 9 * * MON") // Every Monday at 9 AM
//    public void sendWeeklyStatsToPublishers() {
//        List<Long> publisherIds = getActivePublishers();
//
//        for (Long publisherId : publisherIds) {
//            try {
//                Map<String, Object> stats = calculateWeeklyStats(publisherId);
//                notificationService.sendWeeklyStats(publisherId, stats);
//            } catch (Exception e) {
//                log.error("Failed to send weekly stats to publisher: " + publisherId, e);
//            }
//        }
//    }
//}