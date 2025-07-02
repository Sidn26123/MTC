package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponResponse;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CouponMapper {

    Coupon toCoupon(CouponRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Coupon coupon, CouponUpdateRequest request);

    CouponResponse toCouponResponse(Coupon coupon);

    List<CouponResponse> toCouponResponses(List<Coupon> coupons);
}
