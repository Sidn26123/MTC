package com.sidn.metruyenchu.notificationservice.mapper;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationResponse;
import com.sidn.metruyenchu.notificationservice.entity.Notification;
import com.sidn.metruyenchu.notificationservice.entity.NotificationBatch;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationMapper {

    Notification toEntity(NotificationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(NotificationUpdateRequest request, @MappingTarget Notification notification);

    NotificationResponse toResponse(Notification notification);

    List<NotificationResponse> toResponseList(List<Notification> notifications);
}
