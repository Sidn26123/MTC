package com.sidn.metruyenchu.shared_library.utils;

import com.sidn.metruyenchu.shared_library.dto.BaseFilterRequest;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        Sort.Direction direction = Sort.Direction.fromOptionalString(request.getSortOrder())
                .orElse(Sort.Direction.DESC);

        String sortBy = request.getSortBy();
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "createdAt";
        }

        Sort sort = Sort.by(direction, sortBy);

        int page = Math.max(0, request.getPage() - 1);
        int size = request.getSize() > 0 ? request.getSize() : 10; // fallback mặc định là 10

        return PageRequest.of(page, size, sort);
    }


    public static Pageable fromInSpec(BaseFilterRequest request, Sort sort){
        Pageable pageable = PageRequest.of(
                request.getPage() - 1, // Page bắt đầu từ 0
                request.getSize(),
                sort
        );

        return pageable;
    }
}
