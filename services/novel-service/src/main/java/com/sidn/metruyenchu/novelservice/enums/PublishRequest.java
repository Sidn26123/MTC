package com.sidn.metruyenchu.novelservice.enums;

import com.sidn.metruyenchu.novelservice.utils.EnumUtils;

public enum PublishRequest implements EnumUtils<PublishRequest> {
    PENDING, APPROVED, REJECTED, CANCELLED;

    public static PublishRequestAction from(String value) {
        return EnumUtils.from(PublishRequestAction.class, value);
    }
}
