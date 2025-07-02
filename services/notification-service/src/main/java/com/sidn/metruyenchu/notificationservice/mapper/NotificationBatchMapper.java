package com.sidn.metruyenchu.notificationservice.mapper;

import com.sidn.metruyenchu.notificationservice.dto.request.NotificationBatchRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationBatchUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationBatchResponse;
import com.sidn.metruyenchu.notificationservice.entity.NotificationBatch;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationBatchMapper {

    NotificationBatch toEntity(NotificationBatchRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(NotificationBatchUpdateRequest request, @MappingTarget NotificationBatch batch);

    NotificationBatchResponse toResponse(NotificationBatch batch);

    List<NotificationBatchResponse> toResponseList(List<NotificationBatch> batches);
}
