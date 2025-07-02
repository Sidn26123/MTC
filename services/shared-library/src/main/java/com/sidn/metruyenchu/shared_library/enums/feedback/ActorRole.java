package com.sidn.metruyenchu.shared_library.enums.feedback;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum ActorRole {
    PUBLISHER("Người đăng truyện"),
    ADMIN("Quản trị viên"),
    MODERATOR("Người quản lý");
    private final String label;

    ActorRole(String label) {
        this.label = label;
    }

    public static ActorRole from(String value) {
        return EnumUtils.from(ActorRole.class, value);
    }
}
