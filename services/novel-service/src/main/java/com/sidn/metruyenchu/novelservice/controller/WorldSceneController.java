package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.worldScene.WorldSceneCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.WorldSceneResponse;
import com.sidn.metruyenchu.novelservice.service.WorldSceneService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/world-scenes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WorldSceneController {
    WorldSceneService worldSceneService;

    @PostMapping("/create")
    ApiResponse<WorldSceneResponse> createWorldScene(@Valid @RequestBody WorldSceneCreationRequest request) {
        return ApiResponse.<WorldSceneResponse>builder()
                .result(worldSceneService.createWorldScene(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<WorldSceneResponse>> getAllWorldScene() {
        return ApiResponse.<List<WorldSceneResponse>>builder()
                .result(worldSceneService.getAllWorldScene())
                .build();
    }

    @GetMapping("/{worldSceneId}")
    ApiResponse<WorldSceneResponse> getWorldSceneById(@PathVariable String worldSceneId) {
        return ApiResponse.<WorldSceneResponse>builder()
                .result(worldSceneService.getWorldSceneById(worldSceneId))
                .build();
    }

    @DeleteMapping("/{worldSceneId}")
    ApiResponse<String> deleteWorldScene(@PathVariable String worldSceneId) {
        worldSceneService.deleteWorldScene(worldSceneId);
        return ApiResponse.<String>builder()
                .result(String.format("Đã xoá bối cảnh thế giới %s thành công", worldSceneId))
                .build();
    }

    @PutMapping("/{worldSceneId}")
    ApiResponse<WorldSceneResponse> updateWorldScene(@Valid @RequestBody WorldSceneCreationRequest request, @PathVariable String worldSceneId) {
        return ApiResponse.<WorldSceneResponse>builder()
                .result(worldSceneService.updateWorldScene(worldSceneId, request))
                .build();
    }

    @GetMapping("/activatingList")
    ApiResponse<List<WorldSceneResponse>> getActivatingList() {
        return ApiResponse.<List<WorldSceneResponse>>builder()
                .result(worldSceneService.getActiveWorldScene())
                .build();
    }
}
