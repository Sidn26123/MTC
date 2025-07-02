package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.ReadingLogCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ReadingLogResponse;
import com.sidn.metruyenchu.novelservice.entity.ReadingLog;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReadingLogMapper {
    ReadingLog toReadingLog(ReadingLogCreateRequest request);

    ReadingLogResponse toReadingLogResponse(ReadingLog readingLog);

    List<ReadingLogResponse> toReadingLogResponses(List<ReadingLog> logs);
}
