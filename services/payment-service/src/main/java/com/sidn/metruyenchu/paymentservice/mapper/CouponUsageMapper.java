package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUsageRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUsageUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponUsageResponse;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.entity.CouponUsage;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CouponUsageMapper {
    @Mapping(source = "couponId", target = "coupon.id")
    @Mapping(source = "transactionId", target = "transaction.id")
    CouponUsage toEntity(CouponUsageRequest request);

    @Mapping(source = "coupon.id", target = "couponId")
    @Mapping(source = "transaction.id", target = "transactionId")
    CouponUsageResponse toResponse(CouponUsage usage);

    List<CouponUsageResponse> toResponseList(List<CouponUsage> list);
    void updateFromRequest(CouponUsageUpdateRequest request, @MappingTarget CouponUsage entity);
}
