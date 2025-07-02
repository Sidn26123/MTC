package com.sidn.metruyenchu.notificationservice.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.mail")
@Getter
@Setter
public class AppMailProperties {
    private String username;

    @Getter
    @Setter
    public static class TemplateDefaults {
        private String footer;
        private String logoUrl;
    }
}
