package com.sidn.metruyenchu.novelservice.repository.httpclient;

import com.sidn.metruyenchu.novelservice.configuration.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service", url = "http://localhost:8093/file",
        configuration = {AuthenticationRequestInterceptor.class})
public interface FileClient {
    @GetMapping(value = "/files/chapter/{chapterId}/content")
    String getChapterContent(@PathVariable String chapterId);

    @PostMapping(value = "/files/novel/{novelId}/image/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<Object> uploadNovelCover(@PathVariable String novelId, @RequestParam ("file") MultipartFile file);

}
