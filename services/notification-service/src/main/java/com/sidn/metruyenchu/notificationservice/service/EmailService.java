package com.sidn.metruyenchu.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
//    @Autowired
//    private JavaMailSender emailSender;
//
//    public void sendEmail(String toEmail,
//                          String subject,
//                          String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("sidaconchocha@gmail.com");
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(body);
//
//        emailSender.send(message);
//        System.out.println("Email sent to " + toEmail + " successfully........");
//    }
}
