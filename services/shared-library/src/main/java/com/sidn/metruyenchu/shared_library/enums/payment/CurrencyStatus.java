package com.sidn.metruyenchu.shared_library.enums.payment;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum CurrencyStatus implements EnumUtils<CurrencyStatus> {
    ACTIVE("Hoạt động"),
    INACTIVE("Ngừng hoạt động");

    private final String label;

    CurrencyStatus(String label) {
        this.label = label;
    }

    public static CurrencyStatus from(String value) {
        return EnumUtils.from(CurrencyStatus.class, value);
    }
}
