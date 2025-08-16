package com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReadingLogUpdateRequest {
    Integer duration; // đơn vị: giây
    String device;
    String ipAddress;
    String userAgent;
    LocalDateTime endAt;
    Boolean isFinished;
    Float progress; // Tiến độ đọc, giá trị từ 0.0 đến 1.0, với 1.0 là đã đọc hết chương
}
