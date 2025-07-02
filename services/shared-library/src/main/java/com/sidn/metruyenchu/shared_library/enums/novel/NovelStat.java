package com.sidn.metruyenchu.shared_library.enums.novel;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Các thống kê truyện
 */
@Getter
public enum NovelStat implements EnumUtils<NovelStat> {
    WORD_COUNT("Tổng số chữ"),
    AVG_RATE("Điểm trung bình"),
    TOTAL_RATES("Tổng số đánh giá"),
    TOTAL_COMMENTS("Tổng số bình luận"),
    READ_TO_COMMENT("Số chương đọc để bình luận"),
    READ_TO_RATE("Số chương đọc để đánh giá"),
    FULL_SET_PURCHASE_DISCOUNT("Chiết khấu khi mua trọn bộ"),
    FULL_SET_PURCHASE("Số lượt mua trọn bộ");

    private final String label;

    NovelStat(String label) {
        this.label = label;
    }

    public static NovelStat from(String value) {
        return EnumUtils.from(NovelStat.class, value);
    }
}

