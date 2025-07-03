package com.sidn.metruyenchu.identity_service.controller;

import com.sidn.metruyenchu.identity_service.dto.request.ApiResponse;
import com.sidn.metruyenchu.identity_service.dto.request.AuthenticationRequest;
import com.sidn.metruyenchu.identity_service.dto.request.PermissionRequest;
import com.sidn.metruyenchu.identity_service.dto.response.AuthenticationResponse;
import com.sidn.metruyenchu.identity_service.dto.response.PermissionResponse;
import com.sidn.metruyenchu.identity_service.service.AuthenticationService;
import com.sidn.metruyenchu.identity_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {

    PermissionService permissionService;

    AuthenticationService authenticationService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        log.info("Authentication request: {}", request);
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable("permission") String permission){
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().build();
    }
}
