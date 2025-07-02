package com.sidn.metruyenchu.paymentservice.enums;

import com.sidn.metruyenchu.paymentservice.utils.EnumUtils;

public enum PaymentMethodStatus implements EnumUtils<PaymentMethodStatus> {
    ACTIVE("Hoạt động"),
    INACTIVE("Ngừng hoạt động"),;

    private final String label;

    PaymentMethodStatus(String label) {
        this.label = label;
    }

    public static PaymentMethodStatus from(String value) {
        return EnumUtils.from(PaymentMethodStatus.class, value);
    }
}
