package com.sidn.metruyenchu.paymentservice.enums;

import com.sidn.metruyenchu.paymentservice.utils.EnumUtils;

public enum WalletStatus implements EnumUtils<WalletStatus> {
    ACTIVE("Hoạt động"),
    SUSPENDED("Tạm ngưng"),
    CLOSED("Đã đóng");

    private final String label;

    WalletStatus(String label) {
        this.label = label;
    }

    public static WalletStatus from(String value) {
        return EnumUtils.from(WalletStatus.class, value);
    }


}
