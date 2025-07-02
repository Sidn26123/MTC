package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponCollectRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponValidationRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponValidationResult;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponValidationResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.UserCouponResponse;
import com.sidn.metruyenchu.paymentservice.service.CouponService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sidn.metruyenchu.paymentservice.utils.TokenUtils.getUserIdFromContext;


@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/collect")
    public ApiResponse<UserCouponResponse> collectCoupon(
            @RequestBody @Valid CouponCollectRequest request) {
        String userId = getUserIdFromContext();
        UserCouponResponse response = couponService.collectCoupon(
            userId, request.getCouponCode(), request.getSource());
        return ApiResponse.<UserCouponResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/my-coupons")
    public ApiResponse<List<UserCouponResponse>> getMyCoupons(
            @RequestParam(required = false) CouponApplicableType applicableType) {
        String userId = getUserIdFromContext();

        List<UserCouponResponse> coupons = couponService.getUserCoupons(userId, applicableType);
        return ApiResponse.<List<UserCouponResponse>>builder()
                .result(coupons)
                .build();
    }

    @PostMapping("/validate")
    public ApiResponse<CouponValidationResult> validateCoupon(
            @RequestBody @Valid CouponValidationRequest request) {
        String userId = getUserIdFromContext();

        CouponValidationResult result = couponService.validateAndCalculateDiscount(
            userId, request.getCouponCode(), request.getPurchaseAmount(), request.getApplicableType());
        
        return ApiResponse.<CouponValidationResult>builder()
                .result(result)
                .build();
    }
}