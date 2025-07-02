package com.sidn.metruyenchu.paymentservice.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "vip")
@Data
@Component
public class VipProperties {
    
    private int defaultChapterQuota = 100;
    private int maxCouponsPerUser = 50;
    private int couponExpiryCheckDays = 7;
    private boolean autoRenewalEnabled = true;
    private String defaultCurrency = "VND";
    
    private Notification notification = new Notification();
    
    @Data
    public static class Notification {
        private boolean membershipExpiryEnabled = true;
        private int membershipExpiryWarningDays = 3;
        private boolean quotaWarningEnabled = true;
        private int quotaWarningThreshold = 10; // Warn when less than 10 chapters left
    }
}