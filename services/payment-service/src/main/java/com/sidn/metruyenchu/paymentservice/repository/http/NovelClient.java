package com.sidn.metruyenchu.paymentservice.repository.http;

import com.sidn.metruyenchu.paymentservice.configurations.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.paymentservice.dto.response.fiegn.ChapterResponse;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "novel-service", url = "http://localhost:8092/novel",
        configuration = {AuthenticationRequestInterceptor.class})
public interface NovelClient {
    @GetMapping(value = "/chapters/novel/{novelId}/from")
    ApiResponse<List<ChapterResponse>> getFromChaptersByNovelId(@PathVariable String novelId,
                                                                @RequestParam (value = "from", defaultValue = "0") int from,
                                                                @RequestParam (value = "size", defaultValue = "10") int size);
}
