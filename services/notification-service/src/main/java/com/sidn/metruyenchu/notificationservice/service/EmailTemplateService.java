package com.sidn.metruyenchu.notificationservice.service;

import com.sidn.metruyenchu.shared_library.enums.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class EmailTemplateService {
    
    @Autowired
    private TemplateEngine templateEngine;
    
    public String generateEmailContent(NotificationType type, Map<String, String> variables) {
        Context context = new Context();
        variables.forEach(context::setVariable);
        
        String templateName = getEmailTemplateName(type);
        return templateEngine.process(templateName, context);
    }
    
    private String getEmailTemplateName(NotificationType type) {
        switch (type) {
            case STORY_LIKED:
                return "emails/story-liked";
            case STORY_COMMENTED:
                return "emails/story-commented";
            case STORY_REPORTED:
                return "emails/story-reported";
            case REPORT_ASSIGNED:
                return "emails/report-assigned";
            case NEW_CHAPTER:
                return "emails/new-chapter";
            case WEEKLY_STATS:
                return "emails/weekly-stats";
            default:
                return "emails/default-notification";
        }
    }
}