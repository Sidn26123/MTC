package com.sidn.metruyenchu.feedbackservice.dto.response.stat;

import lombok.*;

import java.time.LocalDateTime;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopCommentedNovelsRequest {
    private List<String> novelIds;
    
//    @Min(value = 1, message = "Limit phải lớn hơn 0")
//    @Max(value = 50, message = "Limit không được vượt quá 50")
    @Builder.Default
    private Integer limit = 10;
}