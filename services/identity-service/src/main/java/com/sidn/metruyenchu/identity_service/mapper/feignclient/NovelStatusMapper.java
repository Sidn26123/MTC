package com.sidn.metruyenchu.identity_service.mapper.feignclient;

import com.sidn.metruyenchu.identity_service.dto.request.UserCreationRequest;
import com.sidn.metruyenchu.identity_service.dto.request.feignclient.NovelStatusCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelStatusMapper {

    NovelStatusCreationRequest toNovelStatusCreationRequest(UserCreationRequest request);
}
