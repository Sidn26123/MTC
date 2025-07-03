package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportCommentRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.report.ReportCommentResponse;
import com.sidn.metruyenchu.feedbackservice.service.ReportCommentService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromContext;

@RestController
@RequestMapping("/reports/report-comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Bình luận báo cáo", description = "Quản lý comment của từng báo cáo")
public class ReportCommentController {

    ReportCommentService reportCommentService;

    @Operation(summary = "Tạo bình luận cho một báo cáo")
    @PostMapping
    public ApiResponse<ReportCommentResponse> createReportComment(
            @Valid @RequestBody ReportCommentRequest request
    ) {
        log.info("sd");
        log.info("Creating report comment with request: {}", request.getReportId());
        return ApiResponse.<ReportCommentResponse>builder()
                .result(reportCommentService.createReportComment(request))
                .build();
    }

    @Operation(summary = "Lấy danh sách bình luận theo reportId")
    @GetMapping("/report/{reportId}")
    public ApiResponse<PageResponse<ReportCommentResponse>> getReportComments(
//            @RequestParam String reportId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String reportId) {
        return ApiResponse.<PageResponse<ReportCommentResponse>>builder()
                .result(reportCommentService.getReportComments(reportId, page, size))
                .build();
    }

    @GetMapping("/{commentId}")
    public ApiResponse<ReportCommentResponse> getReportComment(@PathVariable String commentId) {
        ReportCommentResponse response = reportCommentService.getReportComment(commentId);
        return ApiResponse.<ReportCommentResponse>builder()
                .result(response)
                .build();
    }

    @Operation(summary = "Xoá một bình luận trong báo cáo")
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteReportComment(@PathVariable String commentId) {
        reportCommentService.deleteReportComment(commentId);
        return ApiResponse.<Void>builder().build();
    }
}
