package com.sidn.metruyenchu.novelservice.enums;

import com.sidn.metruyenchu.novelservice.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Trạng thái chương truyện
 */
@Getter
public enum ChapterState implements EnumUtils<ChapterState> {
    CREATED("Khởi tạo"),
    PENDING("Chờ duyệt"),
    SUSPENDED("Tạm dừng"),
    REVIEWING("Đang kiểm duyệt");

    private final String label;

    ChapterState(String label) {
        this.label = label;
    }

    public static ChapterState from(String value) {
        return EnumUtils.from(ChapterState.class, value);
    }
}




//
//public enum NovelType implements EnumUtils<NovelType> {
//    TRANS("Dịch"), COMPOSE("Sáng tác");
//    private final String label;
//    NovelType(String label) {
//        this.label = label;
//    }
//
//    public String getLabel() {
//        return label;
//    }
//    public static NovelStat from(String value) {
//        return EnumUtils.from(NovelStat.class, value);
//    }
//}