package com.sidn.metruyenchu.shared_library.dto;

import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseFilterRequest {
    private int page = 1;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortOrder = "DESC";
}
