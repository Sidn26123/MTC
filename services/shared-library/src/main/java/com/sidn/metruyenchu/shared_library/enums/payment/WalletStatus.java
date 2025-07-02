package com.sidn.metruyenchu.shared_library.enums.payment;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

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
