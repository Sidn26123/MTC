package com.sidn.metruyenchu.identity_service.repository.httpclient;

import com.sidn.metruyenchu.identity_service.configuration.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.identity_service.dto.request.feignclient.NovelStatusCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "novel-service", url = "http://localhost:8092/novel",
        configuration = {AuthenticationRequestInterceptor.class})
public interface NovelClient {
//    void createNovelStatus(String token, NovelStatusCreationRequest novelStatusCreationRequest);
    @PostMapping(value = "/novel-status/create", produces = MediaType.APPLICATION_JSON_VALUE)
    Object createNovelStatus(
            @RequestBody NovelStatusCreationRequest request);


}

