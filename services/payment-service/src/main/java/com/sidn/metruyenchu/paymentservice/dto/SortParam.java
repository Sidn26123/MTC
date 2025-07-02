package com.sidn.metruyenchu.paymentservice.dto;

import lombok.Data;

@Data
public class SortParam {
    private String sortBy = "createdAt";
    private String sortDirection = "DESC"; // ASC or DESC
}