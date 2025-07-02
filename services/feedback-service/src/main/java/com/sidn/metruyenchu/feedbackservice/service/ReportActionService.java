package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportActionRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.report.ReportActionResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Report;
import com.sidn.metruyenchu.feedbackservice.entity.ReportAction;
import com.sidn.metruyenchu.feedbackservice.mapper.ReportActionMapper;
import com.sidn.metruyenchu.feedbackservice.repository.ReportActionRepository;
import com.sidn.metruyenchu.feedbackservice.repository.ReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportActionService {
    ReportActionRepository reportActionRepository;
    ReportActionMapper reportActionMapper;
    private final ReportRepository reportRepository;

    // Add methods to handle report actions, such as creating, updating, and retrieving actions

    // Example method to create a report action
    public ReportActionResponse createReportAction(ReportActionRequest request) {
        // Convert request to entity
        ReportAction action = reportActionMapper.toEntity(request);
        Report report = reportRepository.findById(request.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report not found with ID: " + request.getReportId()));
        action.setReport(report);
        log.info("Creating report action for report ID: {}", action.getActionType());

        // Save the action to the repository
        reportActionRepository.save(action);

        // Convert the saved entity back to response DTO
        return reportActionMapper.toResponse(action);
    }


}
