package com.sidn.metruyenchu.shared_library.enums.novel;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Trạng thái truyện
 */
@Getter
public enum NovelState implements EnumUtils<NovelState> {
    CREATED("Khởi tạo"),
    PENDING("Chờ duyệt"),
    SUSPENDED("Tạm dừng"),
    REVIEWING("Đang kiểm duyệt"),
    PUBLISHED("Đã xuất bản"),
    REJECTED("Bị từ chối"),
    DELETED("Đã xóa");

    private final String label;

    NovelState(String label) {
        this.label = label;
    }

    public static NovelState from(String value) {
        return EnumUtils.from(NovelState.class, value);
    }
}
