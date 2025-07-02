package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipPlanRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipPlanUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.VipPlanResponse;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.entity.VipPlan;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VipPlanMapper {
    VipPlan toEntity(VipPlanRequest request);

    VipPlanResponse toResponse(VipPlan plan);

    List<VipPlanResponse> toResponseList(List<VipPlan> plans);
    void updateFromRequest(VipPlanUpdateRequest request, @MappingTarget VipPlan entity);

}
