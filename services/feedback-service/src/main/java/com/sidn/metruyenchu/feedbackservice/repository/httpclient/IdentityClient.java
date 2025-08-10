package com.sidn.metruyenchu.feedbackservice.repository.httpclient;

import com.sidn.metruyenchu.feedbackservice.configuration.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.feign.CheckNovelExistedRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.NovelResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "identity-service", url = "http://localhost:8100/identity",
        configuration = {AuthenticationRequestInterceptor.class})
public interface IdentityClient {
    @GetMapping(value ="/users/getAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<UserResponse>> getAdmins();

}
