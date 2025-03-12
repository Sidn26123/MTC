package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportHandleDetailCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportHandleDetailResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportResponse;
import com.sidn.metruyenchu.feedbackservice.entity.ReportHandleDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface ReportHandleDetailMapper {
    ReportHandleDetail toReportHandleDetail(ReportHandleDetailCreationRequest request);

    ReportHandleDetailResponse toReportHandleDetailResponse(ReportHandleDetail reportHandleDetail);

    void updateReportHandleDetail(@MappingTarget  ReportHandleDetail reportHandleDetail, ReportHandleDetailCreationRequest request);
}
