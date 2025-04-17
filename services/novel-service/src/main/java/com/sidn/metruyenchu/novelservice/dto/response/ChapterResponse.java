package com.sidn.metruyenchu.novelservice.dto.response;

import com.sidn.metruyenchu.novelservice.enums.ChapterState;
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
    String content;
    List<ChapterStatusResponse> chapterStatus;
    Integer amountToUnlock;
    ChapterState chapterState;
    String novel;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted;
    Boolean isActive;
}
