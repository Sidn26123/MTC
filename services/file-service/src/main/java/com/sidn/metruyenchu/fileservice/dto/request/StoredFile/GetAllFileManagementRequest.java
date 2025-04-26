package com.sidn.metruyenchu.fileservice.dto.request.StoredFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllFileManagementRequest {
    @Builder.Default
    PageRequest pageRequest = new PageRequest(0, 10);
    @Builder.Default
    Boolean isDeleted = false;

}
