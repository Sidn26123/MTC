package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportResponse;
import com.sidn.metruyenchu.feedbackservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportController {
    ReportService reportService;

    @PostMapping("/comment")
    ApiResponse<ReportResponse> createReportForComment(
            @Valid @RequestBody ReportCreationRequest request
    ) {
        log.info("a");
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.reportComment(request))
                .build();
    }

    @PostMapping("/rating")
    ApiResponse<ReportResponse> createReportForRating(
            @Valid @RequestBody ReportCreationRequest request
    ) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.reportRating(request))
                .build();
    }

    @PostMapping("/novel")
    ApiResponse<ReportResponse> createReportForNovel(
            @Valid @RequestBody ReportCreationRequest request
    ) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.reportNovel(request))
                .build();
    }

    @PutMapping("/{reportId}")
    ApiResponse<ReportResponse> updateReport(
            @PathVariable String reportId,
            @Valid @RequestBody ReportUpdateRequest request
    ) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.updateReport(reportId, request))
                .build();
    }

    @DeleteMapping("/{reportId}")
    ApiResponse<String> deleteReport(
            @PathVariable String reportId
    ) {

        reportService.deleteReport(reportId);
        return ApiResponse.<String>builder()
                .result("Report deleted")
                .build();
    }

    @PostMapping("/{reportId}/accept")
    ApiResponse<ReportResponse> acceptReport(
            @PathVariable String reportId
    ) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.acceptReport(reportId))
                .build();
    }
}
