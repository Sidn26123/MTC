package com.sidn.metruyenchu.shared_library.enums.payment;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum PaymentRequestStatus implements EnumUtils<PaymentRequestStatus> {
    PENDING("Chờ xử lý"),
    COMPLETED("Hoàn tất"),
    FAILED("Thất bại"),
    CANCELED("Đã hủy"),
    REFUNDED("Đã hoàn tiền"),
    CANCELLED("Đã hủy");
    private final String label;

    PaymentRequestStatus(String label) {
        this.label = label;
    }

    public static PaymentRequestStatus from(String value) {
        return EnumUtils.from(PaymentRequestStatus.class, value);
    }
}
