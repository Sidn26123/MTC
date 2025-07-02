package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipPlanRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipPlanUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.VipPlanResponse;
import com.sidn.metruyenchu.paymentservice.entity.VipPlan;
import com.sidn.metruyenchu.paymentservice.mapper.VipPlanMapper;
import com.sidn.metruyenchu.paymentservice.repository.VipPlanRepository;
import com.sidn.metruyenchu.paymentservice.service.VipMembershipService;
import com.sidn.metruyenchu.paymentservice.service.VipPlanService;
import com.sidn.metruyenchu.paymentservice.utils.PageUtils;
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

@RestController
@RequestMapping("/vip/plans")
@RequiredArgsConstructor
public class VipPlanController {

    private final VipPlanService vipPlanService;

    // 1. Tạo mới plan
    @PostMapping
    public ApiResponse<VipPlanResponse> createVipPlan(@RequestBody @Valid VipPlanRequest request) {
        return ApiResponse.<VipPlanResponse>builder()
                .result(vipPlanService.create(request))
                .build();
    }

    // 2. Cập nhật plan theo ID
    @PutMapping("/{id}")
    public ApiResponse<VipPlanResponse> updateVipPlan(
            @PathVariable String id,
            @RequestBody @Valid VipPlanUpdateRequest request) {
        return ApiResponse.<VipPlanResponse>builder()
                .result(vipPlanService.update(id, request))
                .build();
    }

    // 3. Lấy plan theo ID
    @GetMapping("/{id}")
    public ApiResponse<VipPlanResponse> getVipPlanById(@PathVariable String id) {
        return ApiResponse.<VipPlanResponse>builder()
                .result(vipPlanService.getById(id))
                .build();
    }

    // 4. Lấy tất cả plan (dành cho admin)
    @GetMapping
    public ApiResponse<List<VipPlanResponse>> getAllVipPlans() {
        return ApiResponse.<List<VipPlanResponse>>builder()
                .result(vipPlanService.getAll())
                .build();
    }
}
