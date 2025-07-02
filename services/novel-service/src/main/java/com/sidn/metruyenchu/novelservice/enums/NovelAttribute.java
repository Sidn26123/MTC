package com.sidn.metruyenchu.novelservice.enums;


import com.sidn.metruyenchu.novelservice.utils.EnumUtils;
import lombok.Getter;

/**
 * @author sidn
 * Thuộc tính của truyện
 */
@Getter
public enum NovelAttribute implements EnumUtils<NovelAttribute> {
    FREE("Miễn phí"),
    PAID("Trả phí");
    private final String label;

    NovelAttribute(String label) {
        this.label = label;
    }

    public static NovelAttribute from(String value) {
        return EnumUtils.from(NovelAttribute.class, value);
    }

}
