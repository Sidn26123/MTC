package com.sidn.metruyenchu.shared_library.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class BaseFilterRequest {
    private int page = 1;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortOrder = "DESC";
}
