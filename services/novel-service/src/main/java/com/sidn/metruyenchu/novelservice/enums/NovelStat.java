package com.sidn.metruyenchu.novelservice.enums;

import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.utils.EnumUtils;

public enum NovelStat implements EnumUtils<NovelStat> {
    WORD_COUNT, AVG_RATE, TOTAL_RATES, TOTAL_COMMENTS, READ_TO_COMMENT, READ_TO_RATE, FULL_SET_PURCHASE_DISCOUNT, FULL_SET_PURCHASE;

    public static NovelStat from(String value) {
        return EnumUtils.from(NovelStat.class, value);
    }

}
