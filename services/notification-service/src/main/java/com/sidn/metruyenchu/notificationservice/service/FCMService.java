package com.sidn.metruyenchu.notificationservice.service;

import com.google.firebase.messaging.*;
import com.sidn.metruyenchu.notificationservice.entity.UserToken;
import com.sidn.metruyenchu.notificationservice.repository.UserTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FCMService {

    @Autowired
    FirebaseMessaging firebaseMessaging;
    
    @Autowired
    private UserTokenRepository userTokenRepository;

    // Đăng ký FCM token cho user
    public boolean registerToken(String userId, String token) {
        try {
            Optional<UserToken> existingToken = userTokenRepository.findByUserId(userId);
            log.info("Registering token for userId: {}, token: {}", userId, token);
            if (existingToken.isPresent()) {
                existingToken.get().setFcmToken(token);
                log.info("AS");
                userTokenRepository.save(existingToken.get());
            } else {
                log.info("BS");
                UserToken userToken = UserToken.builder()
                        .userId(userId)
                        .fcmToken(token)
                        .build();
                userTokenRepository.save(userToken);
            }
            
            return true;
        } catch (Exception e) {
            System.err.println("Error registering token: " + e.getMessage());
            return false;
        }
    }
    // Gửi notification đến một user cụ thể
    public String sendNotificationToUser(String userId, String title, String body, Map<String, String> data) {
        try {
            Optional<UserToken> userToken = userTokenRepository.findByUserId(userId);
            
            if (userToken.isEmpty()) {
                throw new RuntimeException("User token not found for userId: " + userId);
            }
            
            Message message = Message.builder()
                    .setToken(userToken.get().getFcmToken())
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putAllData(data != null ? data : Map.of())
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .setNotification(AndroidNotification.builder()
                                    .setIcon("ic_stat_notification")
                                    .setColor("#FF6B6B")
                                    .build())
                            .build())
                    .build();
            
            return firebaseMessaging.send(message);
        } catch (Exception e) {
            System.err.println("Error sending notification: " + e.getMessage());
            throw new RuntimeException("Failed to send notification", e);
        }
    }

    // Gửi notification đến nhiều users
    public BatchResponse sendNotificationToMultipleUsers(List<String> userIds, String title, String body, Map<String, String> data) {
        try {
            List<UserToken> userTokens = userTokenRepository.findByUserIdIn(userIds);
            
            if (userTokens.isEmpty()) {
                throw new RuntimeException("No tokens found for provided user IDs");
            }
            
            List<String> tokens = userTokens.stream()
                    .map(UserToken::getFcmToken)
                    .collect(Collectors.toList());
            
            MulticastMessage message = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putAllData(data != null ? data : Map.of())
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .build())
                    .build();
            
            return firebaseMessaging.sendEachForMulticast(message);
        } catch (Exception e) {
            System.err.println("Error sending notifications to multiple users: " + e.getMessage());
            throw new RuntimeException("Failed to send notifications", e);
        }
    }

    // Gửi notification đến topic (broadcast)
    public String sendNotificationToTopic(String topic, String title, String body, Map<String, String> data) {
        try {
            Message message = Message.builder()
                    .setTopic(topic)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putAllData(data != null ? data : Map.of())
                    .build();
            
            return firebaseMessaging.send(message);
        } catch (Exception e) {
            System.err.println("Error sending topic notification: " + e.getMessage());
            throw new RuntimeException("Failed to send topic notification", e);
        }
    }

    // Test gửi notification trực tiếp bằng token
    public String sendTestNotification(String token, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putData("type", "test")
                    .putData("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();
            
            return firebaseMessaging.send(message);
        } catch (Exception e) {
            System.err.println("Error sending test notification: " + e.getMessage());
            throw new RuntimeException("Failed to send test notification", e);
        }
    }
}
