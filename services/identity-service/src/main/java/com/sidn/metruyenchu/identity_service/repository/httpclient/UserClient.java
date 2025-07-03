package com.sidn.metruyenchu.identity_service.repository.httpclient;

import com.sidn.metruyenchu.identity_service.configuration.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.identity_service.dto.request.feignclient.UserProfileCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8090/user",
        configuration = {AuthenticationRequestInterceptor.class})
public interface UserClient {
    //    void createNovelStatus(String token, NovelStatusCreationRequest novelStatusCreationRequest);
     @PostMapping(value = "/user-profiles", produces = MediaType.APPLICATION_JSON_VALUE)
     Object createUserProfile(
             @RequestBody UserProfileCreationRequest request);

    // Object createUser(@RequestHeader("Authorization") String token, @RequestBody UserCreationRequest request);

}
