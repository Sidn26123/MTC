package com.sidn.metruyenchu.paymentservice.enums;

import com.sidn.metruyenchu.paymentservice.utils.EnumUtils;

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
