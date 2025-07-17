package com.sidn.metruyenchu.shared_library.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatetimeUtils {
    public static LocalDateTime getStartOfWeek(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        return startOfWeek.atStartOfDay();
    }

    public static LocalDateTime fromString(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr);
    }

}
