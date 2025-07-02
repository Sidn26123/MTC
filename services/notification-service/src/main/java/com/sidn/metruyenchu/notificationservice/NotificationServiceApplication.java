package com.sidn.metruyenchu.notificationservice;

import com.sidn.metruyenchu.notificationservice.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class NotificationServiceApplication {
//	@Autowired
//	private EmailService senderService;
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
//	@EventListener(ApplicationReadyEvent.class)
//	public void triggerMail() throws MessagingException {
//		senderService.sendEmail("sidaconkaka@gmail.com",
//				"This is email body",
//				"This is email subject");

//	}
}
