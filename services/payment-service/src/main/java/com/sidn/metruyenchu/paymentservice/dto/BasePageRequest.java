package com.sidn.metruyenchu.paymentservice.dto;

import lombok.Data;

@Data
public class BasePageRequest {
    private int page = 1;
    private int size = 10;
}
