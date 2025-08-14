package com.sidn.metruyenchu.feedbackservice.dto.response.stat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthorCommentStatsResponse {

    long totalComments; // Tổng comment tất cả truyện

    List<PieChartItem> commentsByNovel;
    // Dùng cho Pie chart % comment theo truyện
    // e.g. [{"label": "Truyện A", "value": 120}, ...]

    List<TimeSeriesItem> commentsOverTime;
    // Dùng cho Line chart comment theo thời gian
    // e.g. [{"time": "2025-08-01", "count": 5}, ...]

    List<BarChartItem> topChaptersByComments;
    // Top chương theo lượt comment
    // e.g. [{"label": "Chương 10", "value": 30}, ...]

    List<HeatmapItem> commentsHeatmap;
    // Heatmap comment theo giờ/ngày
    // e.g. [{"dayOfWeek": 1, "hour": 14, "count": 3}, ...]

    List<WordCloudItem> wordCloud;
    // Từ khóa nhiều trong comment
    // e.g. [{"word": "hay quá", "count": 10}, ...]
}
