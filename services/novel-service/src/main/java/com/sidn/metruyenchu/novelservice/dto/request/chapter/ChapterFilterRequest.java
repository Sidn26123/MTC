package com.sidn.metruyenchu.novelservice.dto.request.chapter;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.enums.ChapterState;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterFilterRequest extends BaseFilterRequest {

    // ID của truyện
    String novelId;

    // Tìm kiếm theo tên chương (full hoặc partial)
    String name;

    // Tìm theo publisher cụ thể
    String publisher;

    // Tìm chương có chỉ số cụ thể, hoặc trong khoảng
    Integer chapterIdx;
    Integer minChapterIdx;
    Integer maxChapterIdx;

    // Lọc theo số lượt xem
    Long minViewCount;
    Long maxViewCount;

    // Lọc theo số tiền cần để mở khóa
    Integer minAmountToUnlock;
    Integer maxAmountToUnlock;

    // Lọc theo tổng số bình luận
    Integer minTotalComments;
    Integer maxTotalComments;

    // Trạng thái chương (CREATED, PUBLISHED, etc.)
    ChapterState state;

    // Ngày tạo (có thể theo khoảng)
    LocalDateTime createdAfter;
    LocalDateTime createdBefore;

    LocalDateTime publishedAfter;
    LocalDateTime publishedBefore;
    // Trạng thái kích hoạt
    Boolean isActive;

    // Trạng thái đã xóa
    Boolean isDeleted;

}