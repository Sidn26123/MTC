package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportCommentRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.report.ReportCommentResponse;
import com.sidn.metruyenchu.feedbackservice.entity.ReportComment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReportCommentMapper {
    ReportComment toEntity(ReportCommentRequest request);
    ReportCommentResponse toResponse(ReportComment reportComment);
}
