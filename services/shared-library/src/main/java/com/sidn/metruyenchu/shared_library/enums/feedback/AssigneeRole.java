package com.sidn.metruyenchu.shared_library.enums.feedback;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum AssigneeRole implements EnumUtils<AssigneeRole> {
    PUBLISHER("Người đăng truyện"),
    ADMIN("Quản trị viên"),
    MODERATOR("Người quản lý");
    private final String label;

    AssigneeRole(String label) {
        this.label = label;
    }

    public static AssigneeRole from(String value) {
        return EnumUtils.from(AssigneeRole.class, value);
    }
}
