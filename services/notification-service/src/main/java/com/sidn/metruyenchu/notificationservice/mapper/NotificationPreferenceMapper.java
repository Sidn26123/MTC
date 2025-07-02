package com.sidn.metruyenchu.notificationservice.mapper;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationPreferenceRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationPreferenceUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationPreferenceResponse;
import com.sidn.metruyenchu.notificationservice.entity.NotificationPreference;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationPreferenceMapper {

    NotificationPreference toEntity(NotificationPreferenceRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(NotificationPreferenceUpdateRequest request, @MappingTarget NotificationPreference preference);

    NotificationPreferenceResponse toResponse(NotificationPreference entity);
}
