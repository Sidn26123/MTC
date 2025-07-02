package com.sidn.metruyenchu.novelservice.controller.KafkaController;

import com.sidn.metruyenchu.novelservice.constants.Global;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/feign")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@Slf4j
//public class KafkaController {
//    @KafkaListener(topics = Global.COMMENT_CREATED_TOPIC)
//    public void listen(Object message){
//
//    }
//}
