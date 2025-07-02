package com.sidn.metruyenchu.notificationservice.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EventService {
    
    FCMService1 fcmService1;
    
    public void handleSpecificEvent() throws FirebaseMessagingException {
        // Logic xử lý event của bạn
        
        // Lấy danh sách FCM tokens của users cần thông báo
//        List<String> userTokens = ["token1", "token2", "token3"]; // Đây là ví dụ, bạn cần lấy từ cơ sở dữ liệu hoặc nguồn khác
        String userToken = "cUcwt8jbTfmnErjBDaOMwn:APA91bHZRllbHXTbHVL2mqLaepyEeTXvW7Bs0CuANNewTUAHX4ES1YLhMgJcXtYIbXra8uh_aaZmRQrskfRIa5RrYWN6rtLRapVDRhXvBmMclQ4UJrHSm3U";
        // Gửi notification
//        fcmService.sendToMultipleDevices(
//                Collections.singletonList(userToken),
//            "Tiêu đề thông báo",
//            "Nội dung thông báo"
//        );

        Message message = Message.builder()
                .setToken(userToken)
                .putData("key", "value")
                .build();
        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);
    }
}