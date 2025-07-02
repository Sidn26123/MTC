package com.sidn.metruyenchu.shared_library.enums.novel;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
import lombok.Getter;

/**
 * * @author sidn
 * Kiểu truyện (dịch, sáng tác)
 */
@Getter
public enum NovelType implements EnumUtils<NovelType> {
    TRANS("Dịch"), COMPOSE("Sáng tác");
    private final String label;
    NovelType(String label) {
        this.label = label;
    }

    public static NovelStat from(String value) {
        return EnumUtils.from(NovelStat.class, value);
    }
}
