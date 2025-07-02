package com.sidn.metruyenchu.feedbackservice.dto.request.comment;


import com.sidn.metruyenchu.feedbackservice.dto.PageGetRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentOfUserGetRequest extends PageGetRequest {
    String userId;
}
