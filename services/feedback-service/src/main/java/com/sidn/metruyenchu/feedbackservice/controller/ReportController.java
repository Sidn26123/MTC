package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportResponse;
import com.sidn.metruyenchu.feedbackservice.service.ReportService;
import com.sidn.metruyenchu.shared_library.dto.BaseFilterRequest;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Báo cáo", description = "Quản lý report người dùng gửi lên")
public class ReportController {
    ReportService reportService;

    @Operation(summary = "Tạo báo cáo")
    @PostMapping("/comment")
    ApiResponse<ReportResponse> createReportForComment(
            @Valid @RequestBody ReportCreationRequest request
    ) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.reportComment(request))
                .build();
    }
    @Operation(summary = "Xem danh sách báo cáo của người dùng")
    @GetMapping("/user/{userId}")
    ApiResponse<PageResponse<ReportResponse>> getReportByUserId(
            @PathVariable String userId,
            @RequestParam(value = "role", defaultValue = "USER") String role,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        AssigneeRole userRole = AssigneeRole.valueOf(role.toUpperCase());
        return ApiResponse.<PageResponse<ReportResponse>>builder()
                .result(reportService.getReportsForUser(userId, userRole, pageable))
                .build();
    }

    @GetMapping("/{reportId}")
    ApiResponse<ReportResponse> getReportById(
            @PathVariable String reportId
    ) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.getReportById(reportId))
                .build();
    }

    @GetMapping("/comment/{commentId}")
    ApiResponse<PageResponse<ReportResponse>> getReportsByCommentId(
            @PathVariable String commentId,
            @ModelAttribute BaseFilterRequest filterRequest
            ) {
        return ApiResponse.<PageResponse<ReportResponse>>builder()
                .result(reportService.getReportsByCommentId(commentId, filterRequest))
                .build();
    }

    @GetMapping("/rating/{ratingId}")
    ApiResponse<PageResponse<ReportResponse>> getReportsByRatingId(
            @PathVariable String ratingId,
            @ModelAttribute BaseFilterRequest filterRequest
    ) {
        return ApiResponse.<PageResponse<ReportResponse>>builder()
                .result(reportService.getReportsByRatingId(ratingId, filterRequest))
                .build();
    }

    @GetMapping("/novel/{novelId}")
    ApiResponse<PageResponse<ReportResponse>> getReportsByNovelId(
            @PathVariable String novelId,
            @ModelAttribute BaseFilterRequest filterRequest
    ) {
        return ApiResponse.<PageResponse<ReportResponse>>builder()
                .result(reportService.getReportsByNovelId(novelId, filterRequest))
                .build();
    }



    @PutMapping("/{reportId}/status")
    ApiResponse<ReportResponse> updateReportStatus(
            @PathVariable String reportId,
            @ModelAttribute ReportUpdateRequest status
    ) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.updateReportStatus(reportId, status))
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

    @PostMapping("/chapter")
    ApiResponse<ReportResponse> createReportForChapter(
            @Valid @RequestBody ReportCreationRequest request
    ) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.reportChapter(request))
                .build();
    }




//
//    @PostMapping("/rating")
//    ApiResponse<ReportResponse> createReportForRating(
//            @Valid @RequestBody ReportCreationRequest request
//    ) {
//        return ApiResponse.<ReportResponse>builder()
//                .result(reportService.reportRating(request))
//                .build();
//    }
//
//    @PostMapping("/novel")
//    ApiResponse<ReportResponse> createReportForNovel(
//            @Valid @RequestBody ReportCreationRequest request
//    ) {
//        return ApiResponse.<ReportResponse>builder()
//                .result(reportService.reportNovel(request))
//                .build();
//    }
//
//    @PutMapping("/{reportId}")
//    ApiResponse<ReportResponse> updateReport(
//            @PathVariable String reportId,
//            @Valid @RequestBody ReportUpdateRequest request
//    ) {
//        return ApiResponse.<ReportResponse>builder()
//                .result(reportService.updateReport(reportId, request))
//                .build();
//    }
//
//    @DeleteMapping("/{reportId}")
//    ApiResponse<String> deleteReport(
//            @PathVariable String reportId
//    ) {
//
//        reportService.deleteReport(reportId);
//        return ApiResponse.<String>builder()
//                .result("Report deleted")
//                .build();
//    }
//
//    @PostMapping("/{reportId}/accept")
//    ApiResponse<ReportResponse> acceptReport(
//            @PathVariable String reportId
//    ) {
//        return ApiResponse.<ReportResponse>builder()
//                .result(reportService.acceptReport(reportId))
//                .build();
//    }
}
