package com.sidn.metruyenchu.novelservice.dto.request.worldScene;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorldSceneUpdateRequest {
    String name;
}
