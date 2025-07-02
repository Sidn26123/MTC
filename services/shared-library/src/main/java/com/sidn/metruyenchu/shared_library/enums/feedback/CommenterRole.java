package com.sidn.metruyenchu.shared_library.enums.feedback;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum CommenterRole implements EnumUtils<CommenterRole> {
    REPORTER("Người báo cáo"),
    PUBLISHER("Người đăng truyện"),
    ADMIN("Quản trị viên"),
    MODERATOR("Người quản lý");
    private final String label;

    CommenterRole(String label) {
        this.label = label;
    }

    public static CommenterRole from(String value) {
        return EnumUtils.from(CommenterRole.class, value);
    }
}
