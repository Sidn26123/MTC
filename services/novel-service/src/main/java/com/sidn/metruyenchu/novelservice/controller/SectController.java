package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.sect.SectCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.SectResponse;
import com.sidn.metruyenchu.novelservice.service.SectService;
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
@RequestMapping("/sects")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SectController {
    SectService sectService;

    @PostMapping("/create")
    ApiResponse<SectResponse> createSect(@Valid @RequestBody SectCreationRequest request) {
        return ApiResponse.<SectResponse>builder()
                .result(sectService.createSect(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<SectResponse>> getAllSect() {
        return ApiResponse.<List<SectResponse>>builder()
                .result(sectService.getAllSect())
                .build();
    }

    @GetMapping("/{sectId}")
    ApiResponse<SectResponse> getSectById(@PathVariable String sectId) {
        return ApiResponse.<SectResponse>builder()
                .result(sectService.getSectById(sectId))
                .build();
    }

    @DeleteMapping("/{sectId}")
    ApiResponse<String> deleteSect(@PathVariable String sectId) {
        sectService.deleteSect(sectId);
        return ApiResponse.<String>builder()
                .result(String.format("Sect %s đã xoá thành công", sectId))
                .build();
    }

    @PutMapping("/{sectId}")
    ApiResponse<SectResponse> updateSect(@Valid @RequestBody SectCreationRequest request, @PathVariable String sectId) {
        return ApiResponse.<SectResponse>builder()
                .result(sectService.updateSect(sectId, request))
                .build();
    }

    @GetMapping("/activatingList")
    ApiResponse<List<SectResponse>> getActivatingList() {
        return ApiResponse.<List<SectResponse>>builder()
                .result(sectService.getActivatingList())
                .build();
    }




}
