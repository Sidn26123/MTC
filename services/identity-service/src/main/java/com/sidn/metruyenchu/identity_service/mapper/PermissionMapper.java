package com.sidn.metruyenchu.identity_service.mapper;


import com.sidn.metruyenchu.identity_service.dto.request.PermissionRequest;
import com.sidn.metruyenchu.identity_service.dto.response.PermissionResponse;
import com.sidn.metruyenchu.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
