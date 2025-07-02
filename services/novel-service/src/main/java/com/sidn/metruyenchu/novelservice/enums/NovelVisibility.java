package com.sidn.metruyenchu.novelservice.enums;


/*
PUBLIC: 0
PRIVATE: 1
ACCESS_REQUIRED: 2
Không thay doi thu tu enum vi anh huong gia tri luu xuong DB
 */

import com.sidn.metruyenchu.novelservice.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Mức độ hiển thị truyện
 */
@Getter
public enum NovelVisibility implements EnumUtils<NovelVisibility> {
    PUBLIC("Công khai"),
    PRIVATE("Riêng tư"),
    ACCESS_REQUIRED("Yêu cầu quyền truy cập");

    private final String label;

    NovelVisibility(String label) {
        this.label = label;
    }

    public static NovelVisibility from(String value) {
        return EnumUtils.from(NovelVisibility.class, value);
    }
}
