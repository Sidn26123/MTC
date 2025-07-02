package com.sidn.metruyenchu.paymentservice.mapper;

import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipChapterUsageRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipChapterUsageUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.VipChapterUsageResponse;
import com.sidn.metruyenchu.paymentservice.entity.VipChapterUsage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VipChapterUsageMapper {
    @Mapping(source = "membershipId", target = "membership.id")
    VipChapterUsage toEntity(VipChapterUsageRequest request);

    @Mapping(source = "membership.id", target = "membershipId")
    VipChapterUsageResponse toResponse(VipChapterUsage entity);

    List<VipChapterUsageResponse> toResponseList(List<VipChapterUsage> list);

    void update(@MappingTarget VipChapterUsage entity, VipChapterUsageUpdateRequest request);
}
