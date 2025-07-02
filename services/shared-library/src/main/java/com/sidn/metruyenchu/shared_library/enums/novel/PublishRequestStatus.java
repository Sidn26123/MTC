package com.sidn.metruyenchu.shared_library.enums.novel;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Trạng thái yêu cầu xuất bản
 */
@Getter
public enum PublishRequestStatus implements EnumUtils<PublishRequestStatus> {
    PENDING("Chờ duyệt"),
    APPROVED("Đã duyệt"),
    REJECTED("Từ chối"),
    CANCELLED("Đã hủy");

    private final String label;

    PublishRequestStatus(String label) {
        this.label = label;
    }

    public static PublishRequestStatus from(String value) {
        return EnumUtils.from(PublishRequestStatus.class, value);
    }
}
