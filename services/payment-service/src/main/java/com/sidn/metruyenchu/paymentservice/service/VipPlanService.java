package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.shared_library.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipPlanRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipPlanUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.VipPlanResponse;
import com.sidn.metruyenchu.paymentservice.entity.VipPlan;
import com.sidn.metruyenchu.paymentservice.mapper.VipPlanMapper;
import com.sidn.metruyenchu.paymentservice.repository.VipPlanRepository;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import com.sidn.metruyenchu.shared_library.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VipPlanService {
    VipPlanRepository repository;
    VipPlanMapper mapper;

    public VipPlanResponse create(VipPlanRequest request) {
        VipPlan entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public VipPlanResponse update(String id, VipPlanUpdateRequest request) {
        VipPlan entity = repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        mapper.updateFromRequest(request, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public VipPlanResponse getById(String id) {
        return mapper.toResponse(repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)));
    }

    public List<VipPlanResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    public PageResponse<VipPlanResponse> getAllActivePlans(int page, int size) {
        BaseFilterRequest filterRequest = BaseFilterRequest.builder()
                .page(page)
                .size(size)
                .build();
        Pageable pageable = PageUtils.from(filterRequest);
        Page<VipPlan> vipPlansPage = repository.findByActiveTrueOrderBySortOrder(pageable);

        return PageUtils.toPageResponse(
                vipPlansPage,
            mapper::toResponse,
            filterRequest.getPage()
        );
    }
}
