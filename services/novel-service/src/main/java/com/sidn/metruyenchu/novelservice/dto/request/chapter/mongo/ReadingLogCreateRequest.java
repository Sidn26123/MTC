package com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReadingLogCreateRequest {
    String userId;
    String novelId;
    String chapterId;
    Integer duration; // đơn vị: giây
    String device;
    String ipAddress;
    String userAgent;
    Boolean isFinished;
    Float progress; // Tiến độ đọc, giá trị từ 0.0 đến 1.0, với 1.0 là đã đọc hết chương
}
