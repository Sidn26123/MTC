package com.sidn.metruyenchu.shared_library.enums.notification;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum NotificationType implements EnumUtils<NotificationType> {
    // Story interactions
    STORY_LIKED("Truyện được thích"),
    STORY_FOLLOWED("Truyện được theo dõi"),
    STORY_COMMENTED("Truyện có bình luận mới"),
    STORY_RATED("Truyện được đánh giá"),

    // Publisher activities
    STORY_REPORTED("Truyện bị báo cáo"),
    STORY_APPROVED("Truyện được duyệt"),
    STORY_REJECTED("Truyện bị từ chối"),

    // Report system
    REPORT_ASSIGNED("Báo cáo được phân công"),
    REPORT_STATUS_CHANGED("Trạng thái báo cáo thay đổi"),
    REPORT_COMMENT_ADDED("Có bình luận mới trong báo cáo"),

    // User activities
    NEW_CHAPTER("Chapter mới"),
    NEW_STORY_FROM_AUTHOR("Truyện mới từ tác giả"),

    // System
    SYSTEM_UPDATE("Cập nhật hệ thống"),
    MAINTENANCE("Bảo trì"),
    WEEKLY_STATS("Thống kê tuần");

    private final String label;

    NotificationType(String label) {
        this.label = label;
    }

    public NotificationType from(String label){
        return EnumUtils.from(NotificationType.class, label);
    }

}
