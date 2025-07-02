package com.sidn.metruyenchu.feedbackservice.enums;
import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
public enum TargetType implements EnumUtils<TargetType>{
    NOVEL("Truyện"), CHAPTER("Chương"), COMMENT("Bình luận"), RATING("Đánh giá"), SYSTEM("Hệ thống")
    ;
    private final String label;
    TargetType(String label) {
        this.label = label;
    }

    public static TargetType from(String value) {
        return EnumUtils.from(TargetType.class, value);
    }
}
