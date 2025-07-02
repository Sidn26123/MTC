package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.UserCouponRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.UserCouponUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.UserCouponResponse;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.entity.UserCoupon;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserCouponMapper {
    @Mapping(source = "couponId", target = "coupon.id")
    UserCoupon toEntity(UserCouponRequest request);

    @Mapping(source = "coupon.id", target = "couponId")
    UserCouponResponse toResponse(UserCoupon entity);

    List<UserCouponResponse> toResponseList(List<UserCoupon> list);
    void updateFromRequest(UserCouponUpdateRequest request, @MappingTarget UserCoupon entity);
}
