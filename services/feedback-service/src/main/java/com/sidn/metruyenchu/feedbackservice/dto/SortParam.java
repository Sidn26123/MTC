package com.sidn.metruyenchu.feedbackservice.dto;

import lombok.Data;

@Data
public class SortParam {
    private String sortBy = "createdAt";
    private String sortDirection = "DESC"; // ASC or DESC
}