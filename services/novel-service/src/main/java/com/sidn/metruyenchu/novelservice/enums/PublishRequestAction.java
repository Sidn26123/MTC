package com.sidn.metruyenchu.novelservice.enums;

import com.sidn.metruyenchu.novelservice.utils.EnumUtils;

public enum PublishRequestAction implements EnumUtils<PublishRequestAction> {
    APPROVED, REJECTED, CANCELLED, PUBLISHED, TAKEN;

    public static PublishRequestAction from(String value) {
        return EnumUtils.from(PublishRequestAction.class, value);
    }
}
