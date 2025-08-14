package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.*;
import com.sidn.metruyenchu.feedbackservice.dto.request.feign.CommentNovelRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentStatsResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.ChapterResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.NovelResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.projection.GeneralCountProjectionResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.stat.*;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.feedbackservice.enums.TimeRangeType;
import com.sidn.metruyenchu.shared_library.enums.feedback.FeedbackType;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.CommentMapper;
import com.sidn.metruyenchu.feedbackservice.repository.CommentRepository;
import com.sidn.metruyenchu.feedbackservice.repository.LikeRepository;
import com.sidn.metruyenchu.feedbackservice.repository.httpclient.NovelClient;
import com.sidn.metruyenchu.feedbackservice.spectifications.CommentSpecification;
import com.sidn.metruyenchu.feedbackservice.utils.EnumUtils;
import com.sidn.metruyenchu.feedbackservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.Collectors;

import static com.sidn.metruyenchu.feedbackservice.utils.FeignResponseUtils.callFeignGetChapterInfo;
import static com.sidn.metruyenchu.feedbackservice.utils.FeignResponseUtils.callFeignGetNovelInfo;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getTokenFromContext;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromToken;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentService {
    CommentRepository commentRepository;

    CommentMapper commentMapper;

    NovelClient novelClient;
    private final LikeRepository likeRepository;
    RatingService ratingService;


    public CommentResponse getComment(String commentId) {
        return commentMapper.toCommentResponse(
                commentRepository.findById(commentId).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND))
        );
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }

    public CommentResponse createComment(CommentCreationRequest request){
        String userId = getUserIdFromToken(getTokenFromContext());
        request.setCommentedBy(userId);
        var comment = commentMapper.toComment(request);
        //check nếu feedback type không tồn tại trong enum
        log.info("Feedback type: {}", request.getFeedbackType());
        NovelResponse novelResponse = null;
        ChapterResponse chapterResponse = null;
        //openfeign call to check novel exist
        if (request.getNovelId() != null){
            novelResponse = callFeignGetNovelInfo(novelClient, request.getNovelId()).getResult();

        }
        if (request.getChapterId() != null){
            //openfeign call to check chapter exist
            if (novelResponse == null){
                throw new AppException(ErrorCode.NOVEL_NOT_FOUND);
            }
            chapterResponse = callFeignGetChapterInfo(novelClient, request.getChapterId()).getResult();
            if (chapterResponse == null){
                throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
            }
        }
//        chapterResponse = callFeignGetChapterInfo(novelClient, request.getChapterId()).getResult();


//        updateCommentStat(request.getFeedbackType(), request.getParentId(), 1);
        comment = commentRepository.save(comment);
        if (request.getNovelId() != null && request.getChapterId() != null) {
            novelClient.commentNovel(request.getNovelId(),
                    CommentNovelRequest.builder()
                            .chapterId(request.getChapterId())
                            .novelId(request.getNovelId())
                            .chapterIdx(chapterResponse != null ? chapterResponse.getChapterIdx() : 0)
                            .build());

        }

//        try{
//            comment = commentRepository.save(comment);
//        } catch (DataIntegrityViolationException exception){
//            throw new AppException(ErrorCode.COMMENT_ALREADY_EXISTS);
//        }

        return commentMapper.toCommentResponse(comment);
    }

    /**
     * Action xoá comment
     * @param commentId String
     */
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        //Kieerm tra đã xoá hay chưa
        if (comment.getIsDeleted()){
            throw new AppException(ErrorCode.COMMENT_ALREADY_DELETED);
        }

        commentRepository.deleteById(commentId);

        novelClient.deleteComment(comment.getNovelId(),
                CommentNovelRequest.builder()
                        .chapterId(comment.getChapterId())
                        .novelId(comment.getNovelId())
                        .build());
    }

    public CommentResponse updateComment(String commentId, CommentUpdateRequest request){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        commentMapper.updateComment(comment, request);


        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    public Comment getCommentById(String commentId){
        return commentRepository.findById(commentId).orElseThrow(
                () -> new AppException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }

    public PageResponse<CommentResponse> getCommentInChapter(CommentInChapterGetRequest request){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Page<Comment> comments = commentRepository.findAllByChapterId(request.getChapterId(), pageable);
        List<CommentResponse> commentResponses = comments.map(commentMapper::toCommentResponse).toList();
        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(comments.getTotalPages())
                .totalElements(comments.getTotalElements())
                .data(commentResponses)
                .build();
    }

    public PageResponse<CommentResponse> getCommentInNovel(CommentOfNovelGetRequest request){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Page<Comment> comments = commentRepository.findAllByNovelIdAndIsDeletedIsFalse(request.getNovelId(), pageable);
        List<CommentResponse> commentResponses = comments.map(commentMapper::toCommentResponse).toList();
        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(comments.getTotalPages())
                .totalElements(comments.getTotalElements())
                .data(commentResponses)
                .build();
    }

    public PageResponse<CommentResponse> getCommentOfUser(CommentOfUserGetRequest request){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Page<Comment> comments = commentRepository.findAllByCommentedBy(request.getUserId(), pageable);
        List<CommentResponse> commentResponses = comments.map(commentMapper::toCommentResponse).toList();
        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(comments.getTotalPages())
                .totalElements(comments.getTotalElements())
                .data(commentResponses)
                .build();
    }

    public PageResponse<CommentResponse> filter(CommentFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        Page<Comment> pageData = commentRepository.findAll(CommentSpecification.filter(request), pageable);
        Page<CommentResponse> commentResponses = pageData.map(commentMapper::toCommentResponse);

        if (request.getIsGetChildCommentCount() != null && request.getIsGetChildCommentCount()) {
            List<String> ids = pageData.getContent()
                    .stream()
                    .map(Comment::getId)
                    .toList();
            //Nếu lấy child comment
            if (request.getParentId() != null) {
                List<GeneralCountProjectionResponse> childComments = commentRepository.countChildComments(ids, FeedbackType.COMMENT);
                childComments.forEach(childComment -> {
                    commentResponses.getContent()
                            .stream()
                            .filter(commentResponse -> commentResponse.getId().equals(childComment.getId()))
                            .findFirst()
                            .ifPresent(commentResponse -> {
                                commentResponse.setTotalComments(childComment.getCount());
                            });
                });
            }
        }

        if (request.getIsGetLikeCount() != null && request.getIsGetLikeCount()) {
            List<String> ids = pageData.getContent()
                    .stream()
                    .map(Comment::getId)
                    .toList();
            log.info("ids: {}", ids);
            if (request.getIsGetChildCommentCount() != null) {
                List<GeneralCountProjectionResponse> childComments = likeRepository.countLike(ids, FeedbackType.COMMENT, true);
                childComments.forEach(childComment -> {
                    log.info("child comment: {} {}", childComment.getId(), childComment.getCount());
                    commentResponses.getContent()
                            .stream()
                            .filter(commentResponse -> commentResponse.getId().equals(childComment.getId()))
                            .findFirst()
                            .ifPresent(commentResponse -> {
                                commentResponse.setTotalLike(childComment.getCount());
                            });
                });
            }
        }


        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(commentResponses.stream().toList())
                .build();

    }

    public void incrementTotalLikes(String commentId) {
        commentRepository.incrementTotalLikes(commentId);
    }

    private List<GeneralCountProjectionResponse> countChildComments(List<String> ratingIds, FeedbackType feedbackType) {
        return commentRepository.countChildComments(ratingIds, feedbackType);
    }

    public void decrementTotalLikes(String commentId) {
        commentRepository.decrementTotalLikes(commentId);
    }

    public void incrementTotalDisLikes(String commentId) {
        commentRepository.incrementTotalDisLikes(commentId);
    }

    public void decrementTotalDisLikes(String commentId) {
        commentRepository.decrementTotalDisLikes(commentId);
    }

    /**
     *
     * @param feedbackType
     * @param parentId
     * @param mode 0: not change, 1: increase, -1: decrease
     */
    public void updateCommentStat(com.sidn.metruyenchu.shared_library.enums.feedback.FeedbackType feedbackType, String parentId, int mode){
        if (feedbackType == com.sidn.metruyenchu.shared_library.enums.feedback.FeedbackType.COMMENT){
            Comment comment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
            comment.setTotalReplies(comment.getTotalReplies() + mode);

            commentRepository.save(comment);
        }
        else if (feedbackType == com.sidn.metruyenchu.shared_library.enums.feedback.FeedbackType.RATING){
            ratingService.getRatingEntity(parentId)
                    .ifPresent(rating -> {
                        rating.setTotalReplies(rating.getTotalReplies() + mode);
                        ratingService.saveRating(rating);
                    });
        }

    }

    public List<CommentStatsResponse> getMonthlyStats(List<String> novelIds, int year) {
        boolean isAll = (novelIds == null || novelIds.isEmpty());
        return commentRepository.countMonthly(isAll ? List.of("") : novelIds, year, isAll)
                .stream()
                .map(r -> new CommentStatsResponse(((Number) r[0]).intValue(), ((Number) r[1]).longValue()))
                .toList();
    }

    public List<CommentStatsResponse> getDailyStats(List<String> novelIds, int year, int month) {
        boolean isAll = (novelIds == null || novelIds.isEmpty());
        return commentRepository.countDaily(isAll ? List.of("") : novelIds, year, month, isAll)
                .stream()
                .map(r -> new CommentStatsResponse(((Number) r[0]).intValue(), ((Number) r[1]).longValue()))
                .toList();
    }

    public AuthorCommentStatsResponse getStats(List<String> novelIds, LocalDate from, LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.plusDays(1).atStartOfDay(); // end exclusive

        long totalComments = commentRepository.countTotalComments(novelIds, fromDateTime, toDateTime);

        List<PieChartItem> commentsByNovel = commentRepository.countCommentsByNovel(novelIds, false, fromDateTime, toDateTime)
                .stream()
                .map(row -> PieChartItem.builder()
                        .label((String) row[0]) // novelId, sau này map sang tên truyện ở frontend
                        .value((Long) row[1])
                        .build())
                .toList();

        List<TimeSeriesItem> commentsOverTime = commentRepository.countCommentsOverTime(novelIds, false, fromDateTime, toDateTime)
                .stream()
                .map(row -> TimeSeriesItem.builder()
                        .time(row[0].toString())
                        .count((Long) row[1])
                        .build())
                .toList();

        List<BarChartItem> topChaptersByComments = commentRepository.topChaptersByComments(novelIds, false, fromDateTime, toDateTime)
                .stream()
                .map(row -> BarChartItem.builder()
                        .label((String) row[0]) // chapterId, frontend map sang tên chương
                        .value((Long) row[1])
                        .build())
                .toList();

        List<HeatmapItem> commentsHeatmap = commentRepository.countCommentsHeatmap(novelIds, fromDateTime, toDateTime)
                .stream()
                .map(row -> HeatmapItem.builder()
                        .dayOfWeek(((Number) row[0]).intValue())
                        .hour(((Number) row[1]).intValue())
                        .count((Long) row[2])
                        .build())
                .toList();
        List<WordCloudItem> wordClouds = getWordCloud(novelIds, from, to);
        return AuthorCommentStatsResponse.builder()
                .totalComments(totalComments)
                .commentsByNovel(commentsByNovel)
                .commentsOverTime(commentsOverTime)
                .topChaptersByComments(topChaptersByComments)
                .commentsHeatmap(commentsHeatmap)
                .wordCloud(wordClouds) // word cloud tính ở tầng khác
                .build();
    }


    private static final Set<String> STOPWORDS = Set.of(
            "là", "thì", "mà", "và", "của", "ở", "trong", "ra", "vào", "để", "này", "kia", "cho", "đã", "cũng", "vẫn", "các"
    );

    public List<WordCloudItem> getWordCloud(List<String> novelIds, LocalDate from, LocalDate to) {
        // Lấy toàn bộ content
        List<String> comments = commentRepository.findContentsByNovelIdsAndDateRange(
                novelIds,
                from.atStartOfDay(),
                to.plusDays(1).atStartOfDay()
        );

        Map<String, Integer> freqMap = new HashMap<>();

        for (String content : comments) {
            if (content == null) continue;

            // Chuyển lowercase + bỏ ký tự đặc biệt
            String cleaned = content
                    .toLowerCase()
                    .replaceAll("[^\\p{L}\\p{N}\\s]", " "); // giữ chữ, số, bỏ dấu câu

            // Tách từ (cơ bản, nếu muốn tốt hơn thì tích hợp Underthesea)
            String[] words = cleaned.split("\\s+");

            for (String word : words) {
                if (word.isBlank()) continue;
                if (STOPWORDS.contains(word)) continue;
                if (word.length() < 2) continue;

                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }

        // Chuyển sang list DTO và sắp xếp theo count giảm dần
        return freqMap.entrySet().stream()
                .map(e -> WordCloudItem.builder()
                        .word(e.getKey())
                        .count(e.getValue())
                        .build())
                .sorted((a, b) -> b.getCount() - a.getCount())
                .limit(50) // lấy top 50 từ
                .toList();
    }

    public List<NovelCommentPieChartDto> getTopCommentedNovelsPieChart(List<String> novelIds, Integer limit) {
        List<Object[]> results = commentRepository.countCommentsByNovelIds(novelIds);

        Long totalComments = results.stream()
                .mapToLong(row -> ((Number) row[1]).longValue())
                .sum();

        return results.stream()
                .limit(limit)
                .map(row -> {
                    String novelId = (String) row[0];
                    Long commentCount = ((Number) row[1]).longValue();
                    Double percentage = totalComments > 0 ? (commentCount * 100.0 / totalComments) : 0.0;

                    return NovelCommentPieChartDto.builder()
                            .novelId(novelId)
                            .novelTitle("Novel " + novelId) // TODO: Lấy từ NovelService
                            .commentCount(commentCount)
                            .percentage(Math.round(percentage * 100.0) / 100.0)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * Biểu đồ cột: Comment timeline cho multiple novels
     */
    public List<CommentTimelineDto> getCommentsTimelineForNovels(
            List<String> novelIds,
            TimeRangeType timeRange,
            LocalDate startDate,
            LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        switch (timeRange) {
            case DAILY:
                return getDailyTimeline(novelIds, startDateTime, endDateTime);
            case WEEKLY:
                return getWeeklyTimeline(novelIds, startDateTime, endDateTime);
            case MONTHLY:
                return getMonthlyTimeline(novelIds, startDateTime, endDateTime);
            default:
                throw new IllegalArgumentException("Time range không hỗ trợ cho multiple novels: " + timeRange);
        }
    }

    /**
     * Biểu đồ cột: Comment timeline cho 1 novel với time range linh hoạt
     */
    public List<CommentTimelineDto> getSingleNovelCommentsTimeline(
            String novelId,
            TimeRangeType timeRange,
            LocalDate startDate,
            LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        switch (timeRange) {
            case DAILY:
                return getDailyTimelineForSingleNovel(novelId, startDateTime, endDateTime);
            case WEEKLY:
                return getWeeklyTimeline(Arrays.asList(novelId), startDateTime, endDateTime);
            case MONTHLY:
                return getMonthlyTimeline(Arrays.asList(novelId), startDateTime, endDateTime);
            case HOURLY:
                // Hourly chỉ áp dụng cho 1 ngày
                if (!startDate.equals(endDate)) {
                    throw new IllegalArgumentException("Hourly timeline chỉ áp dụng cho 1 ngày");
                }
                return getHourlyTimeline(novelId, startDate);
            default:
                throw new IllegalArgumentException("Time range không được hỗ trợ: " + timeRange);
        }
    }

    /**
     * Biểu đồ cột: Comment theo giờ trong ngày cho 1 truyện
     */
    public List<CommentTimelineDto> getDailyHourlyCommentsForNovel(String novelId, LocalDate date) {
        return getHourlyTimeline(novelId, date);
    }

    // ========== PRIVATE HELPER METHODS ==========

    private List<CommentTimelineDto> getDailyTimeline(List<String> novelIds, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Object[]> results = commentRepository.countCommentsByDateRange(novelIds, startDateTime, endDateTime);

        return results.stream()
                .map(row -> {
                    LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
                    Long count = ((Number) row[1]).longValue();

                    return CommentTimelineDto.builder()
                            .timeLabel(date.toString())
                            .commentCount(count)
                            .periodStart(date.atStartOfDay())
                            .periodEnd(date.plusDays(1).atStartOfDay().minusSeconds(1))
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<CommentTimelineDto> getDailyTimelineForSingleNovel(String novelId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Object[]> results = commentRepository.countCommentsByDateForSingleNovel(novelId, startDateTime, endDateTime);

        return results.stream()
                .map(row -> {
                    LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
                    Long count = ((Number) row[1]).longValue();

                    return CommentTimelineDto.builder()
                            .timeLabel(date.toString())
                            .commentCount(count)
                            .periodStart(date.atStartOfDay())
                            .periodEnd(date.plusDays(1).atStartOfDay().minusSeconds(1))
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<CommentTimelineDto> getWeeklyTimeline(
            List<String> novelIds,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        List<Object[]> results = commentRepository.countCommentsByWeek(novelIds, startDateTime, endDateTime);

        return results.stream()
                .map(row -> {
                    int year = ((Number) row[0]).intValue(); // ISO year
                    int week = ((Number) row[1]).intValue(); // ISO week (1-53)
                    long count = ((Number) row[2]).longValue();

                    // Xác định ngày bắt đầu tuần (theo ISO)
                    LocalDate weekStart = LocalDate.of(year, 1, 4) // 4 Jan luôn nằm trong tuần 1 của ISO
                            .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                            .with(ChronoField.DAY_OF_WEEK, 1); // Thứ 2

                    return CommentTimelineDto.builder()
                            .timeLabel("Tuần " + week + "/" + year)
                            .commentCount(count)
                            .periodStart(weekStart.atStartOfDay())
                            .periodEnd(weekStart.plusWeeks(1).atStartOfDay().minusSeconds(1))
                            .build();
                })
                .collect(Collectors.toList());
    }


    private List<CommentTimelineDto> getMonthlyTimeline(List<String> novelIds, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Object[]> results = commentRepository.countCommentsByMonth(novelIds, startDateTime, endDateTime);

        return results.stream()
                .map(row -> {
                    Integer year = ((Number) row[0]).intValue();
                    Integer month = ((Number) row[1]).intValue();
                    Long count = ((Number) row[2]).longValue();

                    LocalDate monthStart = LocalDate.of(year, month, 1);

                    return CommentTimelineDto.builder()
                            .timeLabel(String.format("%02d/%d", month, year))
                            .commentCount(count)
                            .periodStart(monthStart.atStartOfDay())
                            .periodEnd(monthStart.plusMonths(1).atStartOfDay().minusSeconds(1))
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<CommentTimelineDto> getHourlyTimeline(String novelId, LocalDate date) {
        List<Object[]> results = commentRepository.countCommentsByHourInDay(novelId, date);

        // Tạo map để fill missing hours với 0
        Map<Integer, Long> hourCountMap = results.stream()
                .collect(Collectors.toMap(
                        row -> ((Number) row[0]).intValue(),
                        row -> ((Number) row[1]).longValue()
                ));

        List<CommentTimelineDto> timeline = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            Long count = hourCountMap.getOrDefault(hour, 0L);
            LocalDateTime hourStart = date.atTime(hour, 0);

            timeline.add(CommentTimelineDto.builder()
                    .timeLabel(String.format("%02d:00", hour))
                    .commentCount(count)
                    .periodStart(hourStart)
                    .periodEnd(hourStart.plusHours(1).minusSeconds(1))
                    .build());
        }

        return timeline;
    }

}
