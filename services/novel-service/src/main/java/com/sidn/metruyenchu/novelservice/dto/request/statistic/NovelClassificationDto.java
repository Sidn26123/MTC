package com.sidn.metruyenchu.novelservice.dto.request.statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelClassificationDto {
    private String id;
    private String name;
    private Long total;
    private String classificationType; // "GENRE", "SECT", "CHARACTER_TRAIT", "PROGRESS_STATUS"
}