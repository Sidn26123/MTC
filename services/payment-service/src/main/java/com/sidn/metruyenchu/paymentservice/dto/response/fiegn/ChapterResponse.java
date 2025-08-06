package com.sidn.metruyenchu.paymentservice.dto.response.fiegn;

import com.sidn.metruyenchu.shared_library.enums.novel.ChapterState;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterResponse {
    String id;
    String name;
    String publisher;
    Integer chapterIdx;
    Long viewCount;
    Integer amountToUnlock;
    ChapterState state;
    String stateLabel;
    String novel;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime publishedAt;
    Boolean isDeleted;
    Boolean isActive;
}