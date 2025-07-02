package com.sidn.metruyenchu.shared_library.enums.novel;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Tiến độ hoàn thành của truyện
 */
@Getter
public enum ProgressStatus implements EnumUtils<ProgressStatus> {
    IN_PROGRESS("Đang tiến hành"),
    COMPLETED("Đã hoàn thành"),
    PAUSED("Tạm dừng"),
    DROPPED("Đã hủy bỏ");

    private final String label;

    ProgressStatus(String label) {
        this.label = label;
    }

    public static ProgressStatus from(String value) {
        return EnumUtils.from(ProgressStatus.class, value);
    }
}
