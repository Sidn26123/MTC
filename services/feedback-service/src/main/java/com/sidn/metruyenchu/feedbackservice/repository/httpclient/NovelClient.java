package com.sidn.metruyenchu.feedbackservice.repository.httpclient;

import com.sidn.metruyenchu.feedbackservice.configuration.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.feign.ChapterUpdateAmountFieldRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.feign.CheckNovelExistedRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.ChapterResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.NovelResponse;
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

    @PostMapping(value = "/novels/chapter/{chapterId}/info")
    Optional<ApiResponse<Object>> updateChapterInfo(@RequestBody ChapterUpdateAmountFieldRequest chapterUpdateAmountFieldRequest,
                                                         @PathVariable String chapterId);
}
