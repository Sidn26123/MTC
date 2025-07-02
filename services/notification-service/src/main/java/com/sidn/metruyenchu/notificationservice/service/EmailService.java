package com.sidn.metruyenchu.notificationservice.service;

import com.sidn.metruyenchu.notificationservice.components.AppMailProperties;
import com.sidn.metruyenchu.shared_library.enums.notification.NotificationType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    JavaMailSender javaMailSender;
    EmailTemplateService emailTemplateService;
    AppMailProperties appMailProperties;
    public void sendPlainTextEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sidaconchocha@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
        log.info("Plain text email sent to {}", toEmail);
    }

    public void sendHtmlEmail(String toEmail, String subject, NotificationType type, Map<String, String> variables) {
        String html = emailTemplateService.generateEmailContent(type, variables);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(appMailProperties.getUsername());
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Email sending failed", e);
        }
    }
}
