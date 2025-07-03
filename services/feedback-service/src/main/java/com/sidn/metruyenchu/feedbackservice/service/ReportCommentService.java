package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.shared_library.dto.BaseFilterRequest;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportCommentRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.report.ReportCommentResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Report;
import com.sidn.metruyenchu.feedbackservice.entity.ReportComment;
import com.sidn.metruyenchu.feedbackservice.mapper.ReportCommentMapper;
import com.sidn.metruyenchu.feedbackservice.repository.ReportCommentRepository;
import com.sidn.metruyenchu.shared_library.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromContext;


@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportCommentService {
    ReportCommentRepository reportCommentRepository;
    ReportService reportService;
    ReportCommentMapper reportCommentMapper;

    public ReportCommentResponse createReportComment(ReportCommentRequest request) {
        if (request.getCommenterId() == null) {
            String userId = getUserIdFromContext();
            request.setCommenterId(userId);
        }
        log.info("Creating report comment with request: {}", request.getReportId());

        Report report = reportService.getReportEntityById(request.getReportId());
        ReportComment reportComment = reportCommentMapper.toEntity(request);
        reportComment.setReport(report);

        reportCommentRepository.save(reportComment);

        return reportCommentMapper.toResponse(reportComment);
    }

    public ReportCommentResponse getReportComment(String id) {
        return reportCommentMapper.toResponse(
                reportCommentRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Report comment not found with ID: " + id))
        );
    }

    public PageResponse<ReportCommentResponse> getReportComments(String reportId, int page, int size) {
        Pageable pageable = PageUtils.from(
                BaseFilterRequest.builder()
                        .page(page)
                        .size(size)
                        .build());
        Page<ReportComment> pageResponse = reportCommentRepository.findByReportId(reportId, pageable);
        return PageUtils.toPageResponse(
                pageResponse,
                reportCommentMapper::toResponse,
                pageable.getPageNumber()
        );
    }

    public Void deleteReportComment(String commentId) {
        ReportComment reportComment = reportCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Report comment not found with ID: " + commentId));

        reportCommentRepository.delete(reportComment);
        return null;
    }
}
