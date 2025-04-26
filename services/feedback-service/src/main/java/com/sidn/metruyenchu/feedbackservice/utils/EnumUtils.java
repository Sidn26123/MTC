package com.sidn.metruyenchu.feedbackservice.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumUtils {
    // Cache cho mỗi enum type với tập hợp các tên enum
    private static final Map<Class<? extends Enum<?>>, Set<String>> CACHE = new ConcurrentHashMap<>();

    /**
     * Kiểm tra xem giá trị value có hợp lệ với enum type cho trước không.
     *
     * @param enumType Kiểu enum (ví dụ: Status.class)
     * @param value    Giá trị cần kiểm tra (ví dụ: "ACTIVE")
     * @param <E>      Generic type cho enum
     * @return true nếu giá trị hợp lệ, false nếu không
     */
    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumType, String value) {
        if (value == null) {
            return false;
        }
        // Lấy tập hợp tên enum từ cache, nếu chưa có thì build mới và cache lại
        Set<String> validNames = CACHE.computeIfAbsent(enumType, key ->
                Arrays.stream(key.getEnumConstants())
                        .map(Enum::name)
                        .collect(Collectors.toSet())
        );
        return validNames.contains(value);
    }

    // Ví dụ sử dụng
}

