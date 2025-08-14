package com.sidn.metruyenchu.feedbackservice.enums;

public enum TimeRangeType {
    DAILY("Ngày"),
    WEEKLY("Tuần"), 
    MONTHLY("Tháng"),
    HOURLY("Giờ");
    
    private final String displayName;
    
    TimeRangeType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}