package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface ReportMapper {
    @Mapping(source = "reportEntityId", target = "reportEntityId")
    Report toReport(ReportCreationRequest request);

    ReportResponse toResponse(Report Report);

    void updateReport(@MappingTarget Report report, ReportUpdateRequest request);

}
