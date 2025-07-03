package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.PaginationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.*;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportHandleDetailResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReportResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.ChapterResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.NovelResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Report;
import com.sidn.metruyenchu.feedbackservice.entity.ReportHandleDetail;
import com.sidn.metruyenchu.feedbackservice.enums.ReportHandleStatus;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import com.sidn.metruyenchu.feedbackservice.enums.TargetType;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.ReportHandleDetailMapper;
import com.sidn.metruyenchu.feedbackservice.mapper.ReportMapper;
import com.sidn.metruyenchu.feedbackservice.repository.ReportHandleDetailRepository;
import com.sidn.metruyenchu.feedbackservice.repository.ReportRepository;
import com.sidn.metruyenchu.feedbackservice.repository.httpclient.NovelClient;
import com.sidn.metruyenchu.shared_library.enums.feedback.ActorRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportType;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import com.sidn.metruyenchu.shared_library.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.sidn.metruyenchu.feedbackservice.utils.FeignResponseUtils.callFeignGetChapterInfo;
import static com.sidn.metruyenchu.feedbackservice.utils.FeignResponseUtils.callFeignGetNovelInfo;
import static com.sidn.metruyenchu.feedbackservice.utils.GeneralUtils.getPageable;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromContext;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportService {

    ReportMapper reportMapper;
    ReportHandleDetailMapper reportHandleDetailMapper;
    NovelClient novelClient;

    ReportRepository reportRepository;
    ReportHandleDetailRepository reportHandleDetailRepository;
    ReportAssignmentService assignmentService;
    ReportActionService reportActionService;

    public ReportResponse createReport(ReportCreationRequest request) {
        String userId = getUserIdFromContext();
        Report report = reportMapper.toReport(request);
        report.setStatus(ReportStatus.PENDING);
        report.setReporterId(userId);
//        NovelResponse novelResponse = callFeignGetNovelInfo(novelClient, request.getReportEntityId()).getResult();
////        ChapterResponse chapterResponse = callFeignGetChapterInfo(novelClient, request.getLastReadChapterId()).getResult();
//
//        if (novelResponse == null || chapterResponse == null){
//            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
//        }

//        if (!Objects.equals(chapterResponse.getNovel(), request.getLastReadChapterId())){
//            throw new AppException(ErrorCode.UNKNOWN_ERROR);
//        }
        Report saved = reportRepository.save(report);
        log.info("Report created: {} - {}", saved.getId(), saved.getReportType());
        autoAssignReport(saved);
        return reportMapper.toResponse(saved);
    }

    /**
     * Báo cáo một comment vi phạm.
     * @param request Thông tin báo cáo bao gồm tiêu đề, mô tả, và thông tin tự động điền.
     * @return Thông tin của báo cáo đã tạo.
     */
    public ReportResponse reportComment(ReportCreationRequest request) {
        request.setReportType(ReportType.COMMENT);
        request.setTargetType(TargetType.COMMENT);
        return createReport(request);
    }

    /**
     * Báo cáo một đánh giá (rating) vi phạm.
     * @param request Thông tin báo cáo bao gồm tiêu đề, mô tả, và thông tin tự động điền.
     * @return Thông tin của báo cáo đã tạo.
     */
    public ReportResponse reportRating(ReportCreationRequest request) {
        request.setReportType(ReportType.RATING);
        request.setTargetType(TargetType.RATING);
        return createReport(request);
    }

    /**
     * Báo cáo một truyện vi phạm.
     * @param request Thông tin báo cáo bao gồm tiêu đề, mô tả, và thông tin tự động điền.
     * @return Thông tin của báo cáo đã tạo.
     */
    public ReportResponse reportNovel(ReportCreationRequest request) {
        request.setReportType(ReportType.NOVEL_CONTENT);
        return createReport(request);
    }

    /**
     * Lấy danh sách các báo cáo vi phạm, chỉ khả dụng cho content mod trở lên.
//     * @param Thông tin xác thực và quyền hạn của người request.
     * @return Danh sách báo cáo dưới dạng phân trang.
     */
    public PageResponse<ReportResponse> getReports(PaginationRequest pagination) {
        Pageable pageable = getPageable(pagination);

        Page<Report> reports = reportRepository.findAll(pageable);
        Page<ReportResponse> reportResponses = reports.map(reportMapper::toResponse);

        return returnPageResponse(reportResponses);
    }


    /**
     * Lấy chi tiết một báo cáo vi phạm.
     * @param reportId ID của báo cáo.
//     * @param request Thông tin xác thực và quyền hạn của người request.
     * @return Chi tiết của báo cáo.
     */
    public ReportHandleDetailResponse getReportById(String reportId) {

        return reportHandleDetailRepository.findById(reportId)
                .map(reportHandleDetailMapper::toReportHandleDetailResponse)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
    }

    public Report getReportEntityById(String reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
    }

    /**
     * Lấy danh sách báo cáo do chính người dùng request tạo ra.
     * @param request Điều kiện lọc báo cáo (ví dụ: phân trang, trạng thái).
     * @return Danh sách báo cáo của người dùng dưới dạng phân trang.
     */
    public PageResponse<ReportResponse> getReportsWithFilter(ReportFilterRequest request) {
        return null;
    }

    /**
     * Cập nhật thông tin của một báo cáo.
     * Nếu báo cáo đã có người xử lý, gửi thông báo tới họ.
     * @param reportId ID của báo cáo.
     * @param request Dữ liệu cần cập nhật.
     * @return Báo cáo sau khi cập nhật.
     */
    public ReportResponse updateReport(String reportId, ReportUpdateRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        reportMapper.updateReport(report, request);

        return reportMapper.toResponse(report);
    }

    /**
     * Xoá một báo cáo vi phạm.
     * Nếu báo cáo đang được xử lý, gửi thông báo tới người xử lý.
     * @param reportId ID của báo cáo.
     */
    public ApiResponse<Void> deleteReport(String reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        reportRepository.delete(report);

        return ApiResponse.<Void>builder().build();
    }



    /**
     * Chấp nhận xử lý một báo cáo vi phạm (dành cho content moderator trở lên).
     * Cập nhật trạng thái báo cáo và gán người xử lý.
     * @param reportId ID của báo cáo.
//     * @param request Thông tin xác thực của người thực hiện.
     * @return Báo cáo sau khi cập nhật trạng thái.
     */
    public ReportResponse acceptReport(String reportId) {


        Report report = reportRepository.findByIdAndIsDeletedIsFalse(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        String userId = getUserIdFromContext();

        if (report.getStatus().equals(ReportStatus.PENDING)){
            report.setStatus(ReportStatus.ACCEPTED);

            // Ghi log xử lý báo cáo
            ReportHandleDetail reportHandleDetail = ReportHandleDetail.builder()
                    .handledBy(userId)
                    .status(ReportHandleStatus.ACCEPTED)
                    .report(report)
                    .build();

            reportHandleDetailRepository.save(reportHandleDetail);
            reportRepository.save(report);

            return reportMapper.toResponse(report);
        }

        return null;
    }

    /**
     * Từ chối xử lý một báo cáo vi phạm (dành cho content moderator trở lên).
     * Cập nhật trạng thái báo cáo và ghi lý do từ chối.
     * @param reportId ID của báo cáo.
     * @param request Dữ liệu bao gồm lý do từ chối.
     * @return Báo cáo sau khi cập nhật trạng thái.
     */
    public ReportResponse rejectReport(String reportId, ReportRejectionRequest request) {
        Report report = reportRepository.findByIdAndIsDeletedIsFalse(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        String userId = getUserIdFromContext();

        if (report.getStatus().equals(ReportStatus.PENDING) || report.getStatus().equals(ReportStatus.ACCEPTED)){
            report.setStatus(ReportStatus.REJECTED);

            // Ghi log xử lý báo cáo
            ReportHandleDetail reportHandleDetail = ReportHandleDetail.builder()
                    .handledBy(userId)
                    .status(ReportHandleStatus.REJECTED)
                    .handlerNote(request.getRejectReason())
                    .report(report)
                    .build();

            reportHandleDetailRepository.save(reportHandleDetail);
            reportRepository.save(report);

            return reportMapper.toResponse(report);
        }

        return null;
    }

//    /**
//     * Cập nhật trạng thái của một báo cáo.
//     * Nếu không có tương tác trong 1 tháng, tự động đóng báo cáo.
//     * @param reportId ID của báo cáo.
//     * @param request Dữ liệu cập nhật trạng thái, tiêu đề, nội dung.
//     * @return Báo cáo sau khi cập nhật.
//     */
//    public ReportResponse updateReportStatus(String reportId, ReportStatusUpdateRequest request) {
//        return null;
//    }
    public <T> PageResponse<T> returnPageResponse(Page<T> pageData) {
        return PageResponse.<T>builder()
                .currentPage(pageData.getNumber() + 1)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent())
                .build();
    }



    private void autoAssignReport(Report report) {
        log.info("Auto-assigning report: {} - {}", report.getId(), report.getReportType() == ReportType.COMMENT);

        switch (report.getReportType()) {
            case NOVEL_CONTENT:
                assignToPublisher(report);
                break;
            case NOVEL_VIOLATION:

            case BUG:
            case SUPPORT:
//                assignToAdmin(report);
                break;
            case COMMENT:
                log.info("S");
                assignToPublisher(report);
                break;
            case RATING:
//                assignToBoth(report);
                break;
        }
    }

    private void assignToPublisher(Report report) {

        String publisherId = getPublisherByTarget(report.getTargetType(), report.getTargetId());
        if (publisherId == null) {
            log.warn("No publisher found for target: {} - {}", report.getTargetType(), report.getTargetId());
            publisherId = "as";
        }
        if (publisherId != null) {

            assignmentService.assignReport(ReportAssignmentRequest.builder()
                    .reportId(report.getId())
                    .assigneeId(publisherId)
                    .assigneeRole(AssigneeRole.PUBLISHER)
                    .isPrimary(false)
                    .build());
        }
    }

    private String getPublisherByTarget(TargetType targetType, String targetId) {
        if (TargetType.NOVEL.equals(targetType)) {
            NovelResponse novel = callFeignGetNovelInfo(novelClient, targetId).getResult();
            return novel != null ? novel.getCurrentPublisher() : null;
        } else if (TargetType.CHAPTER.equals(targetType)) {
            ChapterResponse chapter = callFeignGetChapterInfo(novelClient, targetId).getResult();
            return chapter != null ? chapter.getPublisher() : null;
        }

        return null;
    }

//    public Page<Report> getReportsForUser(String userId, UserRole role, Pageable pageable) {
//        return switch (role) {
//            case UserRole.ADMIN -> reportRepository.findAll(pageable);
//            case UserRole.PUBLISHER -> reportRepository.findByAssignedToAndAssignedRole(userId, UserRole.PUBLISHER, pageable);
//            default -> reportRepository.findByReporterId(userId, pageable);
//        };
//    }

    public PageResponse<ReportResponse> getReportsForUser(String userId, UserRole role, Pageable pageable) {
        Page<Report> reports = switch (role) {
            case ADMIN -> reportRepository.findAll(pageable);
            case PUBLISHER -> reportRepository.findByAssignedToAndAssignedRole(userId, UserRole.PUBLISHER, pageable);
            default -> reportRepository.findByReporterId(userId, pageable);
        };

        return PageUtils.toPageResponse(
                reports,
                reportMapper::toResponse,
                pageable.getPageNumber() + 1
        );
    }

    public ReportResponse updateReportStatus(String reportId, ReportUpdateRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.UNKNOWN_ERROR));

        ReportStatus oldStatus = report.getStatus();
        ReportStatus newStatus = request.getStatus();
        report.setStatus(newStatus);

        if (newStatus == ReportStatus.RESOLVED || newStatus == ReportStatus.REJECTED) {
            report.setResolutionNote(request.getResolutionNote());
            report.setResolvedAt(LocalDateTime.now());
        }

        Report updatedReport = reportRepository.save(report);
        String userId = getUserIdFromContext();
        ActorRole actorRole = ActorRole.ADMIN;


        reportActionService.createReportAction(ReportActionRequest.builder()
                .reportId(reportId)
                .actorId(userId)
                .actorRole(actorRole)
                .actionType(request.getActionType())
                .note(request.getResolutionNote())
                        .oldValue(oldStatus.name())
                        .newValue(newStatus.name())
                .build());


        // Notify reporter
//        notificationService.notifyReportStatusChange(report.getReporterId(), updatedReport);

        return reportMapper.toResponse(updatedReport);
    }

    public List<Report> getOverdueReports() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
        return reportRepository.findByStatusAndCreatedAtBefore(ReportStatus.PENDING, cutoff);
    }


}
