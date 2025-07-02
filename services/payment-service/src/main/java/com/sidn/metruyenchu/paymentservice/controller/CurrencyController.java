package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.ApiResponse;
import com.sidn.metruyenchu.paymentservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.currency.CurrencyCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.currency.CurrencyUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.currency.CurrencyResponse;
import com.sidn.metruyenchu.paymentservice.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CurrencyController {
    CurrencyService currencyService;

    @PostMapping
    public ApiResponse<CurrencyResponse> addCurrency(@Valid @RequestBody CurrencyCreateRequest request) {
        return ApiResponse.<CurrencyResponse>builder()
                .result(currencyService.createCurrency(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CurrencyResponse> getCurrencyById(@PathVariable String id) {
        return ApiResponse.<CurrencyResponse>builder()
                .result(currencyService.getCurrencyById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<CurrencyResponse>> getAllCurrencies(BaseFilterRequest request) {
        return ApiResponse.<PageResponse<CurrencyResponse>>builder()
                .result(currencyService.getAllCurrencies(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CurrencyResponse> updateCurrency(@PathVariable String id, @Valid @RequestBody CurrencyUpdateRequest request) {
        return ApiResponse.<CurrencyResponse>builder()
                .result(currencyService.updateCurrency(id, request))
                .build();
    }

}