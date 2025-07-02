package com.sidn.metruyenchu.notificationservice.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.sidn.metruyenchu.notificationservice.service.EventService;
import com.sidn.metruyenchu.notificationservice.service.FCMService1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
@Slf4j
public class TokenController {
    FCMService1 fcmService1;
    @Autowired
    EventService eventService;
    @PostMapping("/api/token")
    public ResponseEntity<?> updateToken() throws FirebaseMessagingException {
        log.info("A");
        // Lưu hoặc cập nhật FCM token cho user
//        userTokenService.saveToken(request.getUserId(), request.getToken());
//        fcmService.sendToMultipleDevices(Collections.singletonList("fcmService"), "A", "B");
        eventService.handleSpecificEvent();
        return ResponseEntity.ok().build();
    }
}