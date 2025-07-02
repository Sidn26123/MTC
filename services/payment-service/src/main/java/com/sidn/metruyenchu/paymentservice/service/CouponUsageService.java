package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUsageRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUsageUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponUsageResponse;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.entity.CouponUsage;
import com.sidn.metruyenchu.paymentservice.mapper.CouponUsageMapper;
import com.sidn.metruyenchu.paymentservice.repository.CouponUsageRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.List;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponUsageService {
    CouponUsageRepository repository;
    CouponUsageMapper mapper;

    public CouponUsageResponse create(CouponUsageRequest request) {
        CouponUsage entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public CouponUsageResponse update(String id, CouponUsageUpdateRequest request) {
        CouponUsage entity = repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        mapper.updateFromRequest(request, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public CouponUsageResponse getById(String id) {
        return mapper.toResponse(repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)));
    }

    public List<CouponUsageResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    public Integer getTotalUsageCount(String couponId) {
        return repository.countCouponUsageValid(couponId);
    }

    public boolean isCouponCanUsage(Coupon coupon) {
        // Check if the coupon is still valid based on its usage count
        Integer usageCount = getTotalUsageCount(coupon.getId());

        if (coupon.getUsageLimit() != null && usageCount > coupon.getUsageLimit()) {
            return false;
        }

        return true;
    }
}
