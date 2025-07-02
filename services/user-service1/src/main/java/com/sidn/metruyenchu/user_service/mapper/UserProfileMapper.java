package com.sidn.metruyenchu.user_service.mapper;

import com.sidn.metruyenchu.user_service.dto.request.UserProfile.UserProfileCreationRequest;
import com.sidn.metruyenchu.user_service.dto.request.UserProfile.UserProfileUpdateRequest;
import com.sidn.metruyenchu.user_service.dto.response.UserProfile.UserProfileResponse;
import com.sidn.metruyenchu.user_service.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@EnableJpaAuditing
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);
    //    @Mapping(source = "id", target = "id")
    UserProfileResponse toUserProfileResponse(UserProfile UserProfile);
    List<UserProfileResponse> toUserProfileResponses(List<UserProfile> users);

//    @Mapping(target = "roles", ignore = true)
    void updateUserProfile(@MappingTarget UserProfile user, UserProfileUpdateRequest request);
}
