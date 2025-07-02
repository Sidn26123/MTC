package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.request.vip.ChapterReadRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipPurchaseRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipStatusResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.VipMembershipResponse;
import com.sidn.metruyenchu.paymentservice.service.VipMembershipService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.sidn.metruyenchu.paymentservice.utils.TokenUtils.getUserIdFromContext;

@RestController
@RequestMapping("/vip")
@RequiredArgsConstructor
public class VipController {

    private final VipMembershipService vipMembershipService;


//    @GetMapping("/plans")
//    public ApiResponse<PageResponse<VipPlanResponse>> getVipPlans(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
//        return ApiResponse.<PageResponse<VipPlanResponse>>builder()
//                .result(vipPlanService.getAllActivePlans(page, size))
//                .build();
//    }

    @PostMapping("/purchase")
    public ApiResponse<VipMembershipResponse> purchaseVip(
            @RequestBody @Valid VipPurchaseRequest request) {
        String userId = getUserIdFromContext();
        VipMembershipResponse response = vipMembershipService.purchaseVipMembership(userId, request);
        return ApiResponse.<VipMembershipResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/status")
    public ApiResponse<VipStatusResponse> getVipStatus() {
        String userId = getUserIdFromContext();
        VipStatusResponse response = vipMembershipService.getVipStatus(userId);
        return ApiResponse.<VipStatusResponse>builder()
                .result(response)
                .build();
    }

    @PostMapping("/free-chapter")
    public ApiResponse<Void> recordChapterRead(
            @RequestBody @Valid ChapterReadRequest request) {
        String userId = getUserIdFromContext();

        if (!vipMembershipService.canReadVipChapter(userId, request.getChapterId())) {
            throw new AppException(ErrorCode.USER_DONT_HAVE_VIP_ACCESS);
        }

        vipMembershipService.recordChapterRead(userId, request.getStoryId(), request.getChapterId());

        // Trả về ApiResponse<Void>
        return ApiResponse.<Void>builder().build();
    }
}
