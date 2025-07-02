package com.sidn.metruyenchu.fileservice.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileInfo {
    String displayName;
    String contentType;
    String fileName;
    long size;
    String md5Checksum;
    String path;
    String url;
    Boolean isDeleted;
}