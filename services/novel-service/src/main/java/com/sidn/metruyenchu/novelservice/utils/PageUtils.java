package com.sidn.metruyenchu.novelservice.utils;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
}
