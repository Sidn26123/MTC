package com.sidn.metruyenchu.notificationservice.controller;

import com.google.firebase.messaging.BatchResponse;
import com.sidn.metruyenchu.notificationservice.dto.ApiResponse;
import com.sidn.metruyenchu.notificationservice.dto.request.LikeStoryRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.RegisterTokenRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.LikeStoryResponse;
import com.sidn.metruyenchu.notificationservice.dto.response.SendMultipleNotificationResponse;
import com.sidn.metruyenchu.notificationservice.dto.response.SendNotificationResponse;
import com.sidn.metruyenchu.notificationservice.service.FCMService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
//@RequestMapping("/fcm")
//public class FCMController {
//
//    @Autowired
//    private FCMService fcmService;
//
//    // Đăng ký FCM token
//    @PostMapping("/register-token")
//    public ApiResponse<?> registerToken(@RequestBody RegisterTokenRequest request) {
//        try {
//            boolean success = fcmService.registerToken(request.getUserId(), request.getToken());
//
//            if (success) {
//                return ApiResponse.ok(Map.of(
//                    "success", true,
//                    "message", "Token đã được đăng ký thành công"
//                ));
//            } else {
//                return ApiResponse.badRequest().body(Map.of(
//                    "success", false,
//                    "message", "Không thể đăng ký token"
//                ));
//            }
//        } catch (Exception e) {
//            return ApiResponse.internalServerError().body(Map.of(
//                "success", false,
//                "message", "Lỗi server: " + e.getMessage()
//            ));
//        }
//    }
//
//    // API giả lập sự kiện: User like truyện
//    @PostMapping("/like-story")
//    public ApiResponse<?> likeStory(@RequestBody LikeStoryRequest request) {
//        try {
//            Map<String, String> data = Map.of(
//                "type", "story_like",
//                "storyId", request.getStoryId(),
//                "likerUserId", request.getLikerUserId(),
//                "timestamp", String.valueOf(System.currentTimeMillis())
//            );
//
//            String messageId = fcmService.sendNotificationToUser(
//                request.getAuthorUserId(),
//                "❤️ Có người thích truyện của bạn!",
//                "User " + request.getLikerUserId() + " đã thích truyện " + request.getStoryId() + " của bạn",
//                data
//            );
//
//            return ApiResponse.ok(Map.of(
//                "success", true,
//                "message", "Notification đã được gửi",
//                "messageId", messageId
//            ));
//
//        } catch (Exception e) {
//            return ApiResponse.internalServerError().body(Map.of(
//                "success", false,
//                "message", "Không thể gửi notification: " + e.getMessage()
//            ));
//        }
//    }
//
//    // Gửi notification đến một user
//    @PostMapping("/send-to-user")
//    public ApiResponse<?> sendToUser(@RequestParam String userId, @RequestBody NotificationRequest request) {
//        try {
//            String messageId = fcmService.sendNotificationToUser(
//                userId,
//                request.getTitle(),
//                request.getBody(),
//                request.getData()
//            );
//
//            return ApiResponse.ok(Map.of(
//                "success", true,
//                "message", "Notification đã được gửi",
//                "messageId", messageId
//            ));
//
//        } catch (Exception e) {
//            return ApiResponse.internalServerError().body(Map.of(
//                "success", false,
//                "message", "Không thể gửi notification: " + e.getMessage()
//            ));
//        }
//    }
//
//    // Gửi notification đến nhiều users
//    @PostMapping("/send-to-multiple")
//    public ApiResponse<?> sendToMultiple(@RequestBody NotificationRequest request) {
//        try {
//            BatchResponse response = fcmService.sendNotificationToMultipleUsers(
//                request.getUserIds(),
//                request.getTitle(),
//                request.getBody(),
//                request.getData()
//            );
//
//            return ApiResponse.ok(Map.of(
//                "success", true,
//                "message", String.format("Đã gửi %d/%d notifications thành công",
//                    response.getSuccessCount(), request.getUserIds().size()),
//                "successCount", response.getSuccessCount(),
//                "failureCount", response.getFailureCount()
//            ));
//
//        } catch (Exception e) {
//            return ApiResponse.internalServerError().body(Map.of(
//                "success", false,
//                "message", "Không thể gửi notifications: " + e.getMessage()
//            ));
//        }
//    }
//
//    // Gửi broadcast notification
//    @PostMapping("/broadcast")
//    public ApiResponse<?> broadcast(@RequestBody NotificationRequest request) {
//        try {
//            String topic = request.getTopic() != null ? request.getTopic() : "news";
//
//            String messageId = fcmService.sendNotificationToTopic(
//                topic,
//                request.getTitle(),
//                request.getBody(),
//                request.getData()
//            );
//
//            return ApiResponse.ok(Map.of(
//                "success", true,
//                "message", "Broadcast notification đã được gửi",
//                "messageId", messageId
//            ));
//
//        } catch (Exception e) {
//            return ApiResponse.internalServerError().body(Map.of(
//                "success", false,
//                "message", "Không thể gửi broadcast: " + e.getMessage()
//            ));
//        }
//    }
//
//    // Test notification với token trực tiếp
//    @PostMapping("/test")
//    public ApiResponse<?> testNotification(@RequestBody NotificationRequest request) {
//        try {
//            String messageId = fcmService.sendTestNotification(
//                request.getToken(),
//                request.getTitle() != null ? request.getTitle() : "🧪 Test Notification",
//                request.getBody() != null ? request.getBody() : "Đây là test notification từ Spring Boot server"
//            );
//
//            return ApiResponse.ok(Map.of(
//                "success", true,
//                "message", "Test notification đã được gửi",
//                "messageId", messageId
//            ));
//
//        } catch (Exception e) {
//            return ApiResponse.internalServerError().body(Map.of(
//                "success", false,
//                "message", "Không thể gửi test notification: " + e.getMessage()
//            ));
//        }
//    }
//}
@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FCMController {

    FCMService fcmService;

    @PostMapping("/register-token")
    public ApiResponse<SendNotificationResponse> registerToken(@RequestBody RegisterTokenRequest request) {
        try {
            boolean success = fcmService.registerToken(request.getUserId(), request.getToken());
            if (success) {
                return ApiResponse.<SendNotificationResponse>builder()
                        .message("Token đã được đăng ký thành công")
                        .result(SendNotificationResponse.builder()
                                .message("Token registered successfully")
                                .build())
                        .build();
            } else {
                return ApiResponse.<SendNotificationResponse>builder()
                        .code(400)
                        .message("Không thể đăng ký token")
                        .result(SendNotificationResponse.builder()
                                .message("Registration failed")
                                .build())
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.<SendNotificationResponse>builder()
                    .code(500)
                    .message("Lỗi server: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/like-story")
    public ApiResponse<LikeStoryResponse> likeStory(@RequestBody LikeStoryRequest request) {
        try {
            Map<String, String> data = Map.of(
                    "type", "story_like",
                    "storyId", request.getStoryId(),
                    "likerUserId", request.getLikerUserId(),
                    "timestamp", String.valueOf(System.currentTimeMillis())
            );

            String messageId = fcmService.sendNotificationToUser(
                    request.getAuthorUserId(),
                    "❤️ Có người thích truyện của bạn!",
                    "User " + request.getLikerUserId() + " đã thích truyện " + request.getStoryId() + " của bạn",
                    data
            );

            return ApiResponse.<LikeStoryResponse>builder()
                    .message("Notification đã được gửi")
                    .result(LikeStoryResponse.builder()
                            .message("Like notification sent")
                            .messageId(messageId)
                            .build())
                    .build();

        } catch (Exception e) {
            return ApiResponse.<LikeStoryResponse>builder()
                    .code(500)
                    .message("Không thể gửi notification: " + e.getMessage())
                    .build();
        }
    }

//    @PostMapping("/send-to-user")
//    public ApiResponse<SendNotificationResponse> sendToUser(@RequestParam String userId, @RequestBody NotificationRequest request) {
//        try {
//            String messageId = fcmService.sendNotificationToUser(
//                    userId,
//                    request.getTitle(),
//                    request.getContent()
////                    request.getMetadata()
//            );
//
//            return ApiResponse.<SendNotificationResponse>builder()
//                    .message("Notification đã được gửi")
//                    .result(SendNotificationResponse.builder()
//                            .message("Notification sent to user")
//                            .messageId(messageId)
//                            .build())
//                    .build();
//
//        } catch (Exception e) {
//            return ApiResponse.<SendNotificationResponse>builder()
//                    .code(500)
//                    .message("Không thể gửi notification: " + e.getMessage())
//                    .build();
//        }
//    }
//
//    @PostMapping("/send-to-multiple")
//    public ApiResponse<SendMultipleNotificationResponse> sendToMultiple(@RequestBody NotificationRequest request) {
//        try {
//            BatchResponse response = fcmService.sendNotificationToMultipleUsers(
//                    request.getUserIds(),
//                    request.getTitle(),
//                    request.getBody(),
//                    request.getData()
//            );
//
//            return ApiResponse.<SendMultipleNotificationResponse>builder()
//                    .message("Đã gửi notification")
//                    .result(SendMultipleNotificationResponse.builder()
//                            .message("Batch notification sent")
//                            .successCount(response.getSuccessCount())
//                            .failureCount(response.getFailureCount())
//                            .build())
//                    .build();
//
//        } catch (Exception e) {
//            return ApiResponse.<SendMultipleNotificationResponse>builder()
//                    .code(500)
//                    .message("Không thể gửi notifications: " + e.getMessage())
//                    .build();
//        }
//    }
//
//    @PostMapping("/broadcast")
//    public ApiResponse<SendNotificationResponse> broadcast(@RequestBody NotificationRequest request) {
//        try {
//            String topic = request.getTopic() != null ? request.getTopic() : "news";
//
//            String messageId = fcmService.sendNotificationToTopic(
//                    topic,
//                    request.getTitle(),
//                    request.getBody(),
//                    request.getData()
//            );
//
//            return ApiResponse.<SendNotificationResponse>builder()
//                    .message("Broadcast notification đã được gửi")
//                    .result(SendNotificationResponse.builder()
//                            .messageId(messageId)
//                            .message("Broadcast sent")
//                            .build())
//                    .build();
//
//        } catch (Exception e) {
//            return ApiResponse.<SendNotificationResponse>builder()
//                    .code(500)
//                    .message("Không thể gửi broadcast: " + e.getMessage())
//                    .build();
//        }
//    }
//
//    @PostMapping("/test")
//    public ApiResponse<SendNotificationResponse> testNotification(@RequestBody NotificationRequest request) {
//        try {
//            String messageId = fcmService.sendTestNotification(
//                    request.getToken(),
//                    request.getTitle() != null ? request.getTitle() : "🧪 Test Notification",
//                    request.getBody() != null ? request.getBody() : "Đây là test notification từ Spring Boot server"
//            );
//
//            return ApiResponse.<SendNotificationResponse>builder()
//                    .message("Test notification đã được gửi")
//                    .result(SendNotificationResponse.builder()
//                            .messageId(messageId)
//                            .message("Test sent")
//                            .build())
//                    .build();
//
//        } catch (Exception e) {
//            return ApiResponse.<SendNotificationResponse>builder()
//                    .code(500)
//                    .message("Không thể gửi test notification: " + e.getMessage())
//                    .build();
//        }
//    }
}
