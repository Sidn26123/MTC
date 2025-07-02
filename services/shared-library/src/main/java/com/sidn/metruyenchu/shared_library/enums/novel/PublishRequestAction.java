package com.sidn.metruyenchu.shared_library.enums.novel;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
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

