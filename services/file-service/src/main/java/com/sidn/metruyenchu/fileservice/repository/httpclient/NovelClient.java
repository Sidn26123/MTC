package com.sidn.metruyenchu.fileservice.repository.httpclient;

import com.sidn.metruyenchu.fileservice.configuration.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.fileservice.dto.ApiResponse;
import com.sidn.metruyenchu.fileservice.dto.request.feign.CheckNovelExistedRequest;
import com.sidn.metruyenchu.fileservice.dto.request.feign.NovelUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.feign.ChapterResponse;
import com.sidn.metruyenchu.fileservice.dto.response.feign.NovelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "novel-service", url = "http://localhost:8092/novel",
        configuration = {AuthenticationRequestInterceptor.class})
public interface NovelClient {
    @PostMapping(value ="/novels/getNovelById", produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<ApiResponse<NovelResponse>> getNovel(@RequestBody CheckNovelExistedRequest request);

    @PostMapping(value = "/chapters/getChapterById", produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<ApiResponse<ChapterResponse>> getChapter(@RequestBody String chapterId);

    @PutMapping(value = "/novels/{novelId}")
    Optional<ApiResponse<Void>> updateNovel(@PathVariable String novelId, @RequestBody NovelUpdateRequest request);
//    @PostMapping(value = "/novel/updateRating", produces = MediaType.APPLICATION_JSON_VALUE)
//    Optional<ApiResponse<NovelResponse>> updateRating(@RequestBody );

//    @PostMapping(value = "/novels")
}
