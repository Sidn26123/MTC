package com.sidn.metruyenchu.novelservice.utils;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class PageUtils {
    public static <T, R> PageResponse<R> toPageResponse(Page<T> pageData, Function<T, R> mapper, int pageNumber) {
        return PageResponse.<R>builder()
                .currentPage(pageNumber)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(mapper).toList())
                .build();
    }
    public static Pageable from(BaseFilterRequest request) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(request.getSortDirection())
                .orElse(Sort.Direction.DESC);

        Sort sort = Sort.by(direction, Optional.ofNullable(request.getSortBy()).orElse("createdAt"));

        return PageRequest.of(Math.max(0, request.getPage() - 1), request.getSize(), sort);
    }

    public static <T> T mapBaseFilter(BaseFilterRequest from, T to) {
        if (from == null || to == null) return to;

        try {
            // Dùng reflection để gán giá trị cho các field chung
            BeanUtils.copyProperties(from, to, getNullPropertyNames(from));
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy base filter properties", e);
        }

        return to;
    }

    // Loại bỏ các property null khi copy để tránh overwrite dữ liệu đã có
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays.stream(src.getPropertyDescriptors())
                .map(pd -> pd.getName())
                .filter(propertyName -> src.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
