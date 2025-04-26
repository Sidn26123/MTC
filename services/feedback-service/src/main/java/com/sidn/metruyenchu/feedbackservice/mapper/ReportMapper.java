package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportHandleDetailCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportHandleDetailResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Report;
import com.sidn.metruyenchu.feedbackservice.entity.ReportHandleDetail;
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

    @Mapping(source = "id", target = "id")
    ReportResponse toReportResponse(Report Report);

    void updateReport(@MappingTarget Report report, ReportUpdateRequest request);

}
