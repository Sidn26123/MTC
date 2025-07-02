package com.sidn.metruyenchu.shared_library.enums.feedback;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum ReportActionType implements EnumUtils<ReportActionType> {
    ASSIGN("Giao cho người xử lý"),
    STATUS_CHANGE("Thay đổi trạng thái"),
    PRIORITY_CHANGE("Thay đổi mức độ ưu tiên"),
    RESOLVE("Đánh dấu đã xử lý"),
    REOPEN("Mở lại báo cáo"),
    REJECT("Đánh dấu không xử lý");
    private final String label;
    ReportActionType(String label) {
        this.label = label;
    }
    public static ReportActionType from(String value) {
        return EnumUtils.from(ReportActionType.class, value);
    }
}
