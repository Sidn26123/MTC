package com.sidn.metruyenchu.novelservice.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelAuthorCreationRequest {
    String name;
    String profileCoverImage;
    boolean gender;
}
