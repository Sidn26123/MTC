package com.sidn.metruyenchu.novelservice.dto.response.chapter;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReadingLogResponse {
    String id;
    String userId;
    String storyId;
    String chapterId;
    LocalDateTime readAt;
    Integer duration;
    Float progress; // Progress of reading, value from 0.0 to 1.0, with 1.0 meaning the chapter is fully read
    String device;
    String ipAddress;
    String userAgent;
    Boolean isFinished;
}
