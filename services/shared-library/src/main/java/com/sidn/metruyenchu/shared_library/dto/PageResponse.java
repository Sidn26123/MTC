package com.sidn.metruyenchu.shared_library.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private int totalPages;
    private int pageSize;
    private int currentPage;
    private long totalElements;

    @Builder.Default
    private List<T> data = Collections.emptyList();
}
