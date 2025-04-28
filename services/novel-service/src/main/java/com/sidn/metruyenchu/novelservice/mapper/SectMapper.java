package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.sect.SectCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.SectResponse;
import com.sidn.metruyenchu.novelservice.entity.Sect;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SectMapper {
    Sect toSect(SectCreationRequest request);
    @Mapping(source = "id", target = "id")
    SectResponse toSectResponse(Sect sect);

    List<SectResponse> toSectResponses(List<Sect> sects);
}
