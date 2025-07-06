package com.sidn.metruyenchu.novelservice.dto.request.statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopNovelDto {
    private String novelId;
    private String novelName;
    private String novelSlug;
    private Long bookmarkCount;
    private Long viewCount;
    private Float avgRating;
    private Integer rank;

    public TopNovelDto(String aLong, String s, String s1, long l, long l1) {
        this.novelId = String.valueOf(aLong);
        this.novelName = s;
        this.novelSlug = s1;
        this.bookmarkCount = l;
        this.viewCount = l1;
    }
}