package com.sidn.metruyenchu.novelservice.repository.httpclient;

import com.sidn.metruyenchu.novelservice.configuration.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", url = "http://localhost:8096/payment",
        configuration = {AuthenticationRequestInterceptor.class})
public interface PaymentClient {
    @GetMapping(value = "/content-purchases/can-read")
    ApiResponse<Object> getChapterContent(@RequestParam String chapterId, @RequestParam String novelId);
}
