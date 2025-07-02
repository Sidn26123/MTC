package com.sidn.metruyenchu.notificationservice.service;

import com.sidn.metruyenchu.shared_library.enums.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {
    JavaMailSender javaMailSender;

    EmailTemplateService emailTemplateService;

    public void sendEmail(String toEmail,
                          String subject,
                          String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("sidaconchocha@gmail.com");
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(body);
//
//        emailSender.send(message);
        String html = emailTemplateService.generateEmailContent(NotificationType.NEW_CHAPTER, Map.of(
                "storyName", "Co gi hay",
                "author", "Ai la tac gia"
        ));

//        emailService.sendEmail("receiver@gmail.com", "Có chương mới rồi!", html);
        System.out.println("Email sent to " + toEmail + " successfully........");
    }
}
