package com.sidn.metruyenchu.feedbackservice.dto.response.stat;
import com.sidn.metruyenchu.feedbackservice.enums.TimeRangeType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentTimelineRequest {
    @NotEmpty(message = "Novel IDs không được để trống")
    private List<String> novelIds;
    
    @NotNull(message = "Time range không được để trống")
    private TimeRangeType timeRange;
    
    @NotNull(message = "Start date không được để trống")
    private LocalDate startDate;
    
    @NotNull(message = "End date không được để trống")
    private LocalDate endDate;
    
    @AssertTrue(message = "End date phải sau start date")
    public boolean isValidDateRange() {
        return endDate == null || startDate == null || !endDate.isBefore(startDate);
    }
}