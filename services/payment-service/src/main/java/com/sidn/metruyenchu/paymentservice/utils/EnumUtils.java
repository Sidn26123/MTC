package com.sidn.metruyenchu.paymentservice.utils;

public interface EnumUtils<T extends Enum<T>> {
    static <T extends Enum<T>> T from(Class<T> enumType, String value) {
        for (T constant : enumType.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("Invalid value for " + enumType.getSimpleName() + ": " + value);
    }
}
