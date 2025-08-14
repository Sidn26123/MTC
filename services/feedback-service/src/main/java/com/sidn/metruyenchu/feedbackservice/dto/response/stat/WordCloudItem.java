package com.sidn.metruyenchu.feedbackservice.dto.response.stat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Builder
public class WordCloudItem {
    String word;
    int count;
}