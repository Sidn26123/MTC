package com.sidn.metruyenchu.feedbackservice.dto;

import lombok.Data;

@Data
public class BaseFilterRequest {
    private int page = 1;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}
