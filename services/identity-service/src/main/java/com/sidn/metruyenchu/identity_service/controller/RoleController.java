package com.sidn.metruyenchu.identity_service.controller;

import com.sidn.metruyenchu.identity_service.dto.request.ApiResponse;
import com.sidn.metruyenchu.identity_service.dto.request.RoleRequest;
import com.sidn.metruyenchu.identity_service.dto.response.RoleResponse;
import com.sidn.metruyenchu.identity_service.service.PermissionService;
import com.sidn.metruyenchu.identity_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    PermissionService permissionService;
    RoleService roleService;
    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable("role") String role){
        roleService.deleteById(role);
        return ApiResponse.<Void>builder().build();
    }
}
