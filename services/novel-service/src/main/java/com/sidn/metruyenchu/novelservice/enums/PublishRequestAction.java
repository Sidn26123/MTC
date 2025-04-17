package com.sidn.metruyenchu.novelservice.enums;

import com.sidn.metruyenchu.novelservice.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Hành động trong yêu cầu xuất bản
 */
@Getter
public enum PublishRequestAction implements EnumUtils<PublishRequestAction> {
    APPROVED("Phê duyệt"),
    REJECTED("Từ chối"),
    CANCELLED("Hủy"),
    PUBLISHED("Xuất bản"),
    TAKEN("Thu hồi"),
    RECEIVED("Nhận"),;

    private final String label;

    PublishRequestAction(String label) {
        this.label = label;
    }

    public static PublishRequestAction from(String value) {
        return EnumUtils.from(PublishRequestAction.class, value);
    }
}

