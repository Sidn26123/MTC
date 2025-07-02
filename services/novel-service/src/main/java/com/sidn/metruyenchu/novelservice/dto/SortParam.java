package com.sidn.metruyenchu.novelservice.dto;

import lombok.Data;

@Data
public class SortParam {
    private String sortBy = "createdAt";
    private String sortDirection = "DESC"; // ASC or DESC
}