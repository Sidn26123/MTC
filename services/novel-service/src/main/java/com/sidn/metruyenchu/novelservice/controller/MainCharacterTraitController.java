package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.mainTrait.MainCharacterTraitCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.MainCharacterTraitResponse;
import com.sidn.metruyenchu.novelservice.service.MainCharacterTraitService;
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
@RequestMapping("/main-character-traits")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MainCharacterTraitController {
    MainCharacterTraitService mainCharacterTraitService;

    @PostMapping("/create")
    ApiResponse<MainCharacterTraitResponse> createMainCharacterTrait(@Valid @RequestBody MainCharacterTraitCreationRequest request) {
        return ApiResponse.<MainCharacterTraitResponse>builder()
                .result(mainCharacterTraitService.createMainCharacterTrait(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<MainCharacterTraitResponse>> getAllMainCharacterTrait() {
        return ApiResponse.<List<MainCharacterTraitResponse>>builder()
                .result(mainCharacterTraitService.getAllMainCharacterTrait())
                .build();
    }

    @GetMapping("/getAllAvailable")
    ApiResponse<List<MainCharacterTraitResponse>> getAllAvailableMainCharacterTrait() {
        return ApiResponse.<List<MainCharacterTraitResponse>>builder()
                .result(mainCharacterTraitService.getAllAvailableMainCharacterTrait())
                .build();
    }

    @GetMapping("/{mainCharacterTraitId}")
    ApiResponse<MainCharacterTraitResponse> getMainCharacterTraitById(@PathVariable String mainCharacterTraitId) {
        return ApiResponse.<MainCharacterTraitResponse>builder()
                .result(mainCharacterTraitService.getMainCharacterTraitById(mainCharacterTraitId))
                .build();
    }

    @DeleteMapping("/{mainCharacterTraitId}")
    ApiResponse<String> deleteMainCharacterTrait(@PathVariable String mainCharacterTraitId) {
        mainCharacterTraitService.deleteMainCharacterTrait(mainCharacterTraitId);
        return ApiResponse.<String>builder()
                .result(String.format("Main Character Trait %s đã xoá thành công", mainCharacterTraitId))
                .build();
    }

    @PutMapping("/{mainCharacterTraitId}")
    ApiResponse<MainCharacterTraitResponse> updateMainCharacterTrait(@Valid @RequestBody MainCharacterTraitCreationRequest request, @PathVariable String mainCharacterTraitId) {
        return ApiResponse.<MainCharacterTraitResponse>builder()
                .result(mainCharacterTraitService.updateMainCharacterTrait(mainCharacterTraitId, request))
                .build();
    }

    @GetMapping("/{name}")
    ApiResponse<List<MainCharacterTraitResponse>> getMainCharacterTraitByName(@RequestParam String name) {
        return ApiResponse.<List<MainCharacterTraitResponse>>builder()
                .result(mainCharacterTraitService.getMainCharacterTraitByName(name))
                .build();
    }

    @GetMapping("/activatingList")
    ApiResponse<List<MainCharacterTraitResponse>> getActivatingList() {
        return ApiResponse.<List<MainCharacterTraitResponse>>builder()
                .result(mainCharacterTraitService.getActivatingMainCharacterTrait())
                .build();
    }

}
