package com.sidn.metruyenchu.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    public void sendToUser(String userId, String destination, Object payload) {
        messagingTemplate.convertAndSendToUser(
                userId,
                "/queue/" + destination, 
                payload
        );
    }
    
    public void broadcastToAll(String destination, Object payload) {
        messagingTemplate.convertAndSend("/topic/" + destination, payload);
    }
}