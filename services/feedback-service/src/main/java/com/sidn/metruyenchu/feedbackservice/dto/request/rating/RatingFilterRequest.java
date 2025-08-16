package com.sidn.metruyenchu.feedbackservice.dto.request.rating;

import com.sidn.metruyenchu.feedbackservice.dto.BaseFilterRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingFilterRequest extends BaseFilterRequest {
    private String novelId;
    private String ratedBy;
    private String lastReadChapterId;
    private Integer lastReadChapterIdx;

    private Float rateMin;
    private Float rateMax;

    private Float worldBuildingRatingMin;
    private Float worldBuildingRatingMax;

    private Float characterDevelopmentRatingMin;
    private Float characterDevelopmentRatingMax;

    private Float narrativeDepthRatingMin;
    private Float narrativeDepthRatingMax;

    private Boolean isDeleted;
    private Boolean isHidden;

    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private LocalDateTime updatedAfter;
    private LocalDateTime updatedBefore;
}
