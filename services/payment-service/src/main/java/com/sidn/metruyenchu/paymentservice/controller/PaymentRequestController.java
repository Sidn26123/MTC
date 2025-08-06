package com.sidn.metruyenchu.paymentservice.controller;
import com.sidn.metruyenchu.paymentservice.dto.ApiResponse;
import com.sidn.metruyenchu.paymentservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod.PaymentMethodCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod.PaymentMethodUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentRequest.PaymentRequestCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentRequest.PaymentRequestUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentMethod.PaymentMethodResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentRequest.PaymentRequestResponse;
import com.sidn.metruyenchu.paymentservice.service.PaymentMethodService;
import com.sidn.metruyenchu.paymentservice.service.PaymentRequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentRequestController {

    PaymentRequestService paymentRequestService;

    @PostMapping
    public ApiResponse<PaymentRequestResponse> createPaymentRequest(
            @Valid @RequestBody PaymentRequestCreateRequest request) {

        return ApiResponse.<PaymentRequestResponse>builder()
                .result(paymentRequestService.createPaymentRequest(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PaymentRequestResponse> getPaymentRequestById(@PathVariable String id) {
        return ApiResponse.<PaymentRequestResponse>builder()
                .result(paymentRequestService.getPaymentRequestById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PaymentRequestResponse>> getAllPaymentRequests() {
        return ApiResponse.<List<PaymentRequestResponse>>builder()
                .result(paymentRequestService.getAllPaymentRequests())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PaymentRequestResponse> updatePaymentRequest(
            @PathVariable String id,
            @Valid @RequestBody PaymentRequestUpdateRequest request) {

        return ApiResponse.<PaymentRequestResponse>builder()
                .result(paymentRequestService.updatePaymentRequest(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePaymentRequest(@PathVariable String id) {
        paymentRequestService.hardDeletePaymentRequest(id);
        return ApiResponse.<Void>builder().build();
    }
}
