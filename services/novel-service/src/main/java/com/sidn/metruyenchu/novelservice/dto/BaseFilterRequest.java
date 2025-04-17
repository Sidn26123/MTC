package com.sidn.metruyenchu.novelservice.dto;

import lombok.Data;

@Data
public class BaseFilterRequest {
    private int page = 1;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}
