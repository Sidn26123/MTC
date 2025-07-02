package com.sidn.metruyenchu.shared_library.utils;

import java.text.Normalizer;

public class NovelUtils {
    public static String toSlug(String novelDisplayName) {
        if (novelDisplayName == null || novelDisplayName.isEmpty()) {
            return "";
        }

        // Chuẩn hóa Unicode và loại bỏ dấu tiếng Việt
        String normalized = Normalizer.normalize(novelDisplayName, Normalizer.Form.NFD);
        String withoutDiacritics = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Chuyển về chữ thường, thay khoảng trắng và ký tự đặc biệt bằng dấu "-"
        String slug = withoutDiacritics
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // Xóa ký tự đặc biệt, chỉ giữ chữ cái và số
                .replaceAll("\\s+", "-"); // Thay khoảng trắng bằng "-"

        return slug;
    }


}
