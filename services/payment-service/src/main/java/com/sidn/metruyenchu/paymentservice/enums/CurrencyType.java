package com.sidn.metruyenchu.paymentservice.enums;

import com.sidn.metruyenchu.paymentservice.utils.EnumUtils;

public enum CurrencyType implements EnumUtils<CurrencyType> {
    COIN(2),
    TICKET(0);

    private final int scale;

    CurrencyType(int scale) {
        this.scale = scale;
    }

    public static CurrencyStatus from(String value) {
        return EnumUtils.from(CurrencyStatus.class, value);
    }
}
