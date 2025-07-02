package com.sidn.metruyenchu.shared_library.enums.novel;


import com.sidn.metruyenchu.shared_library.utils.EnumUtils;
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
