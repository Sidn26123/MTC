package com.sidn.metruyenchu.identity_service.mapper;

import com.sidn.metruyenchu.identity_service.dto.request.UserCreationRequest;
import com.sidn.metruyenchu.identity_service.dto.request.UserUpdateRequest;
import com.sidn.metruyenchu.identity_service.dto.response.UserResponse;
import com.sidn.metruyenchu.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);
//    @Mapping(source = "id", target = "id")
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponses(List<User> users);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
