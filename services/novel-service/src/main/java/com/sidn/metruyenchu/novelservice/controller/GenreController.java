package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.genre.GenreCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.GenreResponse;
import com.sidn.metruyenchu.novelservice.service.GenreService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GenreController {
    GenreService genreService;

    @PostMapping("/create")
    ApiResponse<GenreResponse> createGenre(@Valid @RequestBody GenreCreationRequest request) {
        return ApiResponse.<GenreResponse>builder()
                .result(genreService.createGenre(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<GenreResponse>> getAllGenre() {
        return ApiResponse.<List<GenreResponse>>builder()
                .result(genreService.getAllGenre())
                .build();
    }

    @GetMapping("/{genreId}")
    ApiResponse<GenreResponse> getGenreById(@PathVariable String genreId) {
        return ApiResponse.<GenreResponse>builder()
                .result(genreService.getGenreById(genreId))
                .build();
    }


    @DeleteMapping("/{genreId}")
    ApiResponse<String> deleteGenre(@PathVariable String genreId) {
        genreService.deleteGenre(genreId);
        return ApiResponse.<String>builder()
                .result(String.format("Genre %s đã xoá thành công", genreId))
                .build();
    }

    @PutMapping("/{genreId}")
    ApiResponse<GenreResponse> updateGenre(@Valid @RequestBody GenreCreationRequest request, @PathVariable String genreId) {
        return ApiResponse.<GenreResponse>builder()
                .result(genreService.updateGenre(genreId, request))
                .build();
    }

    @GetMapping("/activatingList")
    ApiResponse<List<GenreResponse>> getActivatingList() {
        return ApiResponse.<List<GenreResponse>>builder()
                .result(genreService.getActivatingGenre())
                .build();
    }
}
