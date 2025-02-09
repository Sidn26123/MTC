package com.sidn.metruyenchu.notificationservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users-kafka")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class KafkaController {
    @KafkaListener(topics = "user-creation-successful")
    public void listen(Object message) {
//        System.out.println("Consumed message: " + message.toString());
//        log.info("Consumed message: {}", message.toString());
        log.info("Consumed message: {}", message);
    }

}
