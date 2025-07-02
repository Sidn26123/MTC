package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportAssignmentRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.report.ReportAssignmentResponse;
import com.sidn.metruyenchu.feedbackservice.entity.ReportAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReportAssignmentMapper {
    ReportAssignment toEntity(ReportAssignmentRequest request);
    ReportAssignmentResponse toResponse(ReportAssignment reportAssignment);

}
