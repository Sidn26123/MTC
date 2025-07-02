package com.sidn.metruyenchu.notificationservice.mapper;

import com.sidn.metruyenchu.notificationservice.dto.request.NotificationTemplateRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationTemplateUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationTemplateResponse;
import com.sidn.metruyenchu.notificationservice.entity.NotificationTemplate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationTemplateMapper {
    NotificationTemplate toEntity(NotificationTemplateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(NotificationTemplateUpdateRequest request, @MappingTarget NotificationTemplate template);

    NotificationTemplateResponse toResponse(NotificationTemplate template);

    List<NotificationTemplateResponse> toResponseList(List<NotificationTemplate> templates);
}
