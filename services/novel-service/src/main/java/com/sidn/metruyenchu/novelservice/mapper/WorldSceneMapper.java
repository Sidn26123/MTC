package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.worldScene.WorldSceneCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.WorldSceneResponse;
import com.sidn.metruyenchu.novelservice.entity.WorldScene;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorldSceneMapper {
    WorldScene toWorldScene(WorldSceneCreationRequest worldScene);
    @Mapping(source = "id", target = "id")
    WorldSceneResponse toWorldSceneResponse(WorldScene worldScene);
    List<WorldSceneResponse> toWorldSceneResponse(List<WorldScene> worldScenes);
}
