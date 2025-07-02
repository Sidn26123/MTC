package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.mainTrait.MainCharacterTraitCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.MainCharacterTraitResponse;
import com.sidn.metruyenchu.novelservice.entity.MainCharacterTrait;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MainCharacterTraitMapper {
    MainCharacterTrait toMainCharacterTrait(MainCharacterTraitCreationRequest request);

    @Mapping(source = "id", target = "id")
    MainCharacterTraitResponse toMainCharacterTraitResponse(MainCharacterTrait mainCharacterTrait);

    List<MainCharacterTraitResponse> toMainCharacterTraitResponses(List<MainCharacterTrait> mainCharacterTraits);
}
