package com.sidn.metruyenchu.feedbackservice.dto.request.comment;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import reactor.util.annotation.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreationRequest {
    String content;
    String commentedBy;

    String parentId;

    @NonNull
    FeedbackType feedbackType;

    String novelId;

    String chapterId;


}
