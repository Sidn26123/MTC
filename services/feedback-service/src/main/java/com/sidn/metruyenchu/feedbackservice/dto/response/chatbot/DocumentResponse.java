package com.sidn.metruyenchu.feedbackservice.dto.response.chatbot;

import com.sidn.metruyenchu.shared_library.enums.feedback.FeedbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import reactor.util.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentResponse {
    Long id;
    String name;
    LocalDateTime uploadedAt;
    String uploadedBy;
    Boolean isActive;
    List<DocumentChunkResponse> chunks;
}