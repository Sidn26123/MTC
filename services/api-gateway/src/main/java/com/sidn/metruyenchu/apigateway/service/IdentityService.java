package com.sidn.metruyenchu.apigateway.service;

import com.sidn.metruyenchu.apigateway.dto.ApiResponse;
import com.sidn.metruyenchu.apigateway.dto.request.IntrospectRequest;
import com.sidn.metruyenchu.apigateway.dto.response.IntrospectResponse;
import com.sidn.metruyenchu.apigateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token){

        return identityClient.introspect(IntrospectRequest.builder()
                        .token(token)
                      .build());
    }
}
