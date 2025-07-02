package com.sidn.metruyenchu.novelservice.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reading_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadingLog {

    @Id
    private String id;

    private String userId;
    private String storyId;
    private String chapterId;

    private LocalDateTime readAt;

    private Integer duration; // thời gian đọc chương, đơn vị giây
    private Float progress; // Tiến độ đọc, giá trị từ 0.0 đến 1.0, với 1.0 là đã đọc hết chương
    private String device;
    private String ipAddress;
    private String userAgent;
    private Boolean isFinished;
}