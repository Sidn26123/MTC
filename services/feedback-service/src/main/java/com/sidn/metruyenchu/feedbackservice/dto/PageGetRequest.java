package com.sidn.metruyenchu.feedbackservice.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageGetRequest {
    int page = 1;
    int size = 10;
}
