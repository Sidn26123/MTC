package com.sidn.metruyenchu.shared_library.enums.payment;

import com.sidn.metruyenchu.shared_library.utils.EnumUtils;

public enum TransactionType implements EnumUtils<TransactionType> {
    DEPOSIT("Nạp tiền"),
    WITHDRAW("Rút tiền"),
    PURCHASE("Mua hàng"),
    VIP_BENEFIT("Quyền lợi VIP"),
    REFUND("Hoàn tiền"),
    GIFT("Quà tặng"),
    COUPON("Coupon"),
    ;

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

    public static TransactionType from(String value) {
        return EnumUtils.from(TransactionType.class, value);
    }
}
