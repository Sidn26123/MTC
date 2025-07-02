package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.ApiResponse;
import com.sidn.metruyenchu.paymentservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod.PaymentMethodCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod.PaymentMethodUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentMethod.PaymentMethodResponse;
import com.sidn.metruyenchu.paymentservice.service.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-methods")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentMethodController {

    PaymentMethodService paymentMethodService;

    @PostMapping
    public ApiResponse<PaymentMethodResponse> createPaymentMethod(
            @Valid @RequestBody PaymentMethodCreateRequest request) {

        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.createPaymentMethod(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PaymentMethodResponse> getPaymentMethodById(@PathVariable String id) {
        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.getPaymentMethodById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<PaymentMethodResponse>> getAllPaymentMethods(BaseFilterRequest request) {
        return ApiResponse.<PageResponse<PaymentMethodResponse>>builder()
                .result(paymentMethodService.getAllPaymentMethods(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PaymentMethodResponse> updatePaymentMethod(
            @PathVariable String id,
            @Valid @RequestBody PaymentMethodUpdateRequest request) {

        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.updatePaymentMethod(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePaymentMethod(@PathVariable String id) {
        paymentMethodService.hardDeletePaymentMethod(id);
        return ApiResponse.<Void>builder().build();
    }
}
