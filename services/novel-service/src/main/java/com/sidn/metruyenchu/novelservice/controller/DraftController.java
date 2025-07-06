package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.draft.DraftCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.genre.GenreCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.GenreResponse;
import com.sidn.metruyenchu.novelservice.dto.response.draft.DraftResponse;
import com.sidn.metruyenchu.novelservice.service.DraftService;
import com.sidn.metruyenchu.novelservice.service.GenreService;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drafts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DraftController {
    DraftService draftService;

    @PostMapping("/create")
    ApiResponse<DraftResponse> createDraft(@Valid @RequestBody DraftCreationRequest request) {
        return ApiResponse.<DraftResponse>builder()
                .result(draftService.createDraft(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<DraftResponse> getDraft(@PathVariable("id") String draftId) {
        return ApiResponse.<DraftResponse>builder()
                .result(draftService.getDraft(draftId))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<DraftResponse>> getDrafts(
            @RequestParam("publisher") String publisher,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<DraftResponse>>builder()
                .result(draftService.getDrafts(publisher, page, size))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteDraft(@PathVariable("id") String draftId) {
        draftService.softDeleteDraft(draftId);
        return ApiResponse.<Void>builder().build();
    }
}
