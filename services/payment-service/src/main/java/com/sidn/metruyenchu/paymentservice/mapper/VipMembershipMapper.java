package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipMembershipRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.VipMembershipResponse;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.entity.VipMembership;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VipMembershipMapper {

    @Mapping(source = "planId", target = "plan.id")
    VipMembership toEntity(VipMembershipRequest request);

    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "purchaseTransaction.id", target = "purchaseTransactionId")
    VipMembershipResponse toResponse(VipMembership entity);

    List<VipMembershipResponse> toResponseList(List<VipMembership> list);
}
