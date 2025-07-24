package com.sidn.metruyenchu.shared_library.utils;

import com.sidn.metruyenchu.shared_library.dto.BaseFilterRequest;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
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

    public static BaseFilterRequest toBaseFilter(Object from) {
        if (from == null) return  BaseFilterRequest.builder().build(); // Trả về filter mặc định

        BaseFilterRequest filter = BaseFilterRequest.builder().build(); // Tạo với default value

        try {
            BeanUtils.copyProperties(from, filter, getNullPropertyNames(from));
        } catch (Exception e) {
            throw new RuntimeException("Failed to map entity to BaseFilterRequest", e);
        }

        return filter;
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
