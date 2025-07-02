package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportAssignmentRequest;
import com.sidn.metruyenchu.feedbackservice.entity.Report;
import com.sidn.metruyenchu.feedbackservice.entity.ReportAssignment;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.ReportAssignmentMapper;
import com.sidn.metruyenchu.feedbackservice.repository.ReportAssignmentRepository;
import com.sidn.metruyenchu.feedbackservice.repository.ReportRepository;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportAssignmentService {

    ReportAssignmentRepository assignmentRepository;
    ReportAssignmentMapper reportAssignmentMapper;
    ReportRepository reportRepository;


    public void assignReport(ReportAssignmentRequest request) {
        ReportAssignment assignment = reportAssignmentMapper.toEntity(request);
        Report reportEntity = reportRepository.findById(request.getReportId())
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
        assignment.setReport(reportEntity);
        assignmentRepository.save(assignment);
    }

    public List<ReportAssignment> getAssignments(String reportId) {
        return assignmentRepository.findByReportId(reportId);
    }
}