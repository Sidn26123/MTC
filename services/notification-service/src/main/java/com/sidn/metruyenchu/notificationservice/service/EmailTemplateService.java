package com.sidn.metruyenchu.notificationservice.service;

import com.sidn.metruyenchu.shared_library.enums.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    private final TemplateEngine templateEngine;

    public String generateEmailContent(NotificationType type, Map<String, String> variables) {
        Context context = new Context();
        variables.forEach(context::setVariable);

        String templateName = getEmailTemplateName(type);
        return templateEngine.process(templateName, context);
    }

    private String getEmailTemplateName(NotificationType type) {
        return switch (type) {
            case STORY_LIKED -> "emails/story-liked";
            case STORY_COMMENTED -> "emails/story-commented";
            case STORY_REPORTED -> "emails/story-reported";
            case REPORT_ASSIGNED -> "emails/report-assigned";
            case NEW_CHAPTER -> "emails/new-chapter";
            case WEEKLY_STATS -> "emails/weekly-stats";
            default -> "emails/default-notification";
        };
    }
}
