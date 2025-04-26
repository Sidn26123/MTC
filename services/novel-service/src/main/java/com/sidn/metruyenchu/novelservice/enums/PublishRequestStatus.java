package com.sidn.metruyenchu.novelservice.enums;

import com.sidn.metruyenchu.novelservice.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Trạng thái yêu cầu xuất bản
 */
@Getter
public enum PublishRequest implements EnumUtils<PublishRequest> {
    PENDING("Chờ duyệt"),
    APPROVED("Đã duyệt"),
    REJECTED("Từ chối"),
    CANCELLED("Đã hủy");

    private final String label;

    PublishRequest(String label) {
        this.label = label;
    }

    public static PublishRequest from(String value) {
        return EnumUtils.from(PublishRequest.class, value);
    }
}
