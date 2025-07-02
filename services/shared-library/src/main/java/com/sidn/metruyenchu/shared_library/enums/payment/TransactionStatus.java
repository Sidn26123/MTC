package com.sidn.metruyenchu.shared_library.enums.payment;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum TransactionStatus implements EnumUtils<TransactionStatus> {
    PENDING("Chờ xử lý"),
    COMPLETED("Hoàn tất"),
    FAILED("Thất bại"),
    CANCELED("Đã hủy"),
    REFUNDED("Đã hoàn tiền"),;
    private final String label;

    TransactionStatus(String label) {
        this.label = label;
    }

    public static TransactionStatus from(String value) {
        return EnumUtils.from(TransactionStatus.class, value);
    }
}
