package com.sidn.metruyenchu.paymentservice.dto.response.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VNPayResponse {
    public String code;
    public String message;
    public String paymentUrl;
}