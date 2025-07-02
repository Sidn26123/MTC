package com.sidn.metruyenchu.feedbackservice.mapper;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportActionRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportAssignmentRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.report.ReportActionResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.report.ReportAssignmentResponse;
import com.sidn.metruyenchu.feedbackservice.entity.ReportAction;
import com.sidn.metruyenchu.feedbackservice.entity.ReportAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReportActionMapper {
    ReportAction toEntity(ReportActionRequest request);

    ReportActionResponse toResponse(ReportAction action);

    List<ReportActionResponse> toResponses(List<ReportAction> actions);
}
