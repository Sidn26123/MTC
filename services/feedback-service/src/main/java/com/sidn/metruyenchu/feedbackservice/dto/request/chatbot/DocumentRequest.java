package com.sidn.metruyenchu.feedbackservice.dto.request.chatbot;

import com.sidn.metruyenchu.shared_library.enums.feedback.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import reactor.util.annotation.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentRequest {
    String name;
    String uploadedBy;
    Boolean isActive;
}