package com.sidn.metruyenchu.novelservice.dto.request.statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelInteractionDto {
    private Long totalBookmarks;
    private Long totalViews;
    private Long totalRatings;
    private Long totalComments;
}