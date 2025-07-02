package com.sidn.metruyenchu.shared_library.enums.novel;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Các thống kê chương truyện (generic)
 */
@Getter
public enum ChapterStatGenericList implements EnumUtils<ChapterStatGenericList> {
    VIEW_COUNT("Lượt xem"),
    TOTAL_COMMENT("Tổng bình luận"),
    AMOUNT_TO_UNLOCK("Số xu để mở khóa");

    private final String label;

    ChapterStatGenericList(String label) {
        this.label = label;
    }

    public static ChapterStatGenericList from(String value) {
        return EnumUtils.from(ChapterStatGenericList.class, value);
    }
}
