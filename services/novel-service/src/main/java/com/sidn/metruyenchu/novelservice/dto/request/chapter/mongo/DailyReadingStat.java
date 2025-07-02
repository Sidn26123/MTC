package com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailyReadingStat {
    @Field("_id")
    String date; // yyyy-MM-dd
    long totalLogs;
    long totalDuration;
    long finishedCount;
}