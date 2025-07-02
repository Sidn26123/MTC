package com.sidn.metruyenchu.shared_library.enums.feedback;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum ReportType implements EnumUtils<ReportType> {
    NOVEL_CONTENT("Nội dung truyện"),
    NOVEL_VIOLATION("Vi phạm truyện"),
    COMMENT("Bình luận"),
    RATING("Đánh giá"),
    BUG("Lỗi hệ thống"),
    FEATURE_REQUEST("Yêu cầu tính năng"),
    SUPPORT("Hỗ trợ");
    private final String label;

    ReportType(String label) {
        this.label = label;
    }

    public static ReportType from(String value) {
        return EnumUtils.from(ReportType.class, value);
    }
}
