package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.response.CommentStatsResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.stat.*;
import com.sidn.metruyenchu.feedbackservice.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StatsService {
    CommentRepository commentRepository;

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
        boolean isAll = (novelIds == null || novelIds.isEmpty());
        List<PieChartItem> commentsByNovel = commentRepository.countCommentsByNovel(novelIds, isAll, fromDateTime, toDateTime)
                .stream()
                .map(row -> PieChartItem.builder()
                        .label((String) row[0]) // novelId, sau này map sang tên truyện ở frontend
                        .value((Long) row[1])
                        .build())
                .toList();

        List<TimeSeriesItem> commentsOverTime = commentRepository.countCommentsOverTime(novelIds, isAll, fromDateTime, toDateTime)
                .stream()
                .map(row -> TimeSeriesItem.builder()
                        .time(row[0].toString())
                        .count((Long) row[1])
                        .build())
                .toList();

        List<BarChartItem> topChaptersByComments = commentRepository.topChaptersByComments(novelIds, isAll, fromDateTime, toDateTime)
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

        return AuthorCommentStatsResponse.builder()
                .totalComments(totalComments)
                .commentsByNovel(commentsByNovel)
                .commentsOverTime(commentsOverTime)
                .topChaptersByComments(topChaptersByComments)
                .commentsHeatmap(commentsHeatmap)
                .wordCloud(Collections.emptyList()) // word cloud tính ở tầng khác
                .build();
    }
}
