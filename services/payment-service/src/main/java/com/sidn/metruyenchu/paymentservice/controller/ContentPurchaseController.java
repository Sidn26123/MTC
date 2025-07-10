package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.ApiResponse;
import com.sidn.metruyenchu.paymentservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.CheckUserCanReadContentRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.CheckUserPurchaseContentRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.ContentPurchaseRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.contentPurchase.ContentPurchaseResponse;
import com.sidn.metruyenchu.paymentservice.service.ContentPurchaseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content-purchases")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ContentPurchaseController {
    private final ContentPurchaseService contentPurchaseService;

    /**
     * Purchase content
     * @param request the purchase request
     * @return the purchase response
     */
    @PostMapping
    public ApiResponse<ContentPurchaseResponse> purchaseContent(
            @Valid @RequestBody ContentPurchaseRequest request) {
        ContentPurchaseResponse response = contentPurchaseService.purchaseContent(request);
        return ApiResponse.<ContentPurchaseResponse>builder()
                .result(response)
                .build();
    }

    /**
     * Get purchase by ID
     * @param id the purchase ID
     * @return the purchase response
     */
    @GetMapping("/{id}")
    public ApiResponse<ContentPurchaseResponse> getPurchaseById(@PathVariable String id) {
        return ApiResponse.<ContentPurchaseResponse>builder()
                .result(contentPurchaseService.getPurchaseById(id))
                .build();
    }

    /**
     * Get purchases by user ID
     * @param userId the user ID
     * @return list of purchase responses
     */
    @GetMapping
    public ApiResponse<PageResponse<ContentPurchaseResponse>> getPurchasesByUserId(@RequestParam String userId, BaseFilterRequest request) {
        PageResponse<ContentPurchaseResponse> purchases = contentPurchaseService.getPurchasesByUserId(userId, request);
        return ApiResponse.<PageResponse<ContentPurchaseResponse>>builder()
                .result(purchases)
                .build();
    }

    @GetMapping("/check")
    public ApiResponse<Boolean> checkContentPurchased(
            @ModelAttribute CheckUserPurchaseContentRequest request) {
        boolean isPurchased = contentPurchaseService.hasPurchasedContent(request);
        return ApiResponse.<Boolean>builder()
                .result(isPurchased)
                .build();
    }

    @GetMapping("/can-read")
    public ApiResponse<Boolean> checkUserCanReadContent(
            @ModelAttribute CheckUserCanReadContentRequest request) {
        boolean isPurchased = contentPurchaseService.hasPurchasedNovelOrChapter(request);
        return ApiResponse.<Boolean>builder()
                .result(isPurchased)
                .build();
    }

}