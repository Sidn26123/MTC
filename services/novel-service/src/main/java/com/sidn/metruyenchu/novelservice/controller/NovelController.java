package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.feignclient.feedbackService.DeleteRatingRequest;
import com.sidn.metruyenchu.novelservice.dto.request.feignclient.feedbackService.RatingRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.*;
import com.sidn.metruyenchu.novelservice.dto.request.publish.NovelPublishRequestCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.publish.PublishRequestActionLogCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.publish.PublishRequestActionLogUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelResponse;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelCanPublishResponse;
import com.sidn.metruyenchu.novelservice.dto.response.publish.NovelPublishRequestResponse;
import com.sidn.metruyenchu.novelservice.dto.response.publish.PublishRequestActionLogResponse;
import com.sidn.metruyenchu.novelservice.service.NovelPublishRequestService;
import com.sidn.metruyenchu.novelservice.service.NovelService;
import com.sidn.metruyenchu.novelservice.service.PublishRequestActionLogService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelController {
    NovelService novelService;
    NovelPublishRequestService novelPublishRequestService;
    PublishRequestActionLogService publishRequestActionLogService;
    @PostMapping("/create")
    ApiResponse<NovelResponse> createNovel(@Valid @RequestBody NovelCreationRequest request) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.createNovel(request))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<NovelResponse>> getAllNovels(
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size
            @ModelAttribute BaseFilterRequest request
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getAllNovels(request))
                .build();
    }

//    @GetMapping("/filter")
//    ApiResponse<PageResponse<NovelResponse>> getNovelByCategory(
//            @ModelAttribute NovelFilterRequest request
//            ) {
//        return ApiResponse.<PageResponse<NovelResponse>>builder()
//                .result(novelService.getNovels(request))
//                .build();
//    }
//
    @PostMapping("/filter")
    ApiResponse<PageResponse<NovelResponse>> getNovelByCategory(
            @RequestBody NovelFilterRequest request
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovels(request))
                .build();
    }

    @GetMapping("/{novelSlug}")
    ApiResponse<NovelResponse> getNovel(@PathVariable String novelSlug) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.getNovelBySlug(novelSlug))
                .build();
    }

    @PostMapping("/getNovelById")
    ApiResponse<NovelResponse> getNovelById(@RequestBody CheckNovelExistedRequest request) {
        NovelResponse novelResponse;

        if (request.getId() != null){
            novelResponse = novelService.getNovelById(request.getId());
        } else if (request.getSlug() != null){
            novelResponse = novelService.getNovelBySlug(request.getSlug());
        }
        else{
            throw new RuntimeException("Id or slug must be provided");
        }
        return ApiResponse.<NovelResponse>builder()
                .result(novelResponse)
                .build();
    }

    @PutMapping("/{novelId}")
    ApiResponse<NovelResponse> updateNovel(@PathVariable String novelId, @Valid @RequestBody NovelUpdateRequest request) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.updateNovel(novelId, request))
                .build();
    }

    @DeleteMapping("/{novelId}")
    ApiResponse<String> deleteNovel(@PathVariable String novelId) {
        novelService.deleteNovel(novelId);
        return ApiResponse.<String>builder()
                .result(String.format("Novel %s đã xoá thành công", novelId))
                .build();
    }

//    @GetMapping("/search")
//    ApiResponse<List<NovelResponse>> searchNovel(@RequestParam String keyword){
//
//    }

    @GetMapping("/{novelId}/chapters")
    void getChapters(@PathVariable String novelId){
    }

//    @GetMapping("/{novelId}/chapters/{chapterId}")

    @GetMapping("/get-novel-by-author/{authorId}")
    ApiResponse<List<NovelResponse>> getNovelByAuthor(@PathVariable String authorId) {
        return ApiResponse.<List<NovelResponse>>builder()
                .result(novelService.getNovelByAuthorId(authorId))
                .build();
    }

    @GetMapping("/all")
    ApiResponse<Integer> getAllNovel(@RequestParam boolean containDeleted) {
        return ApiResponse.<Integer>builder()
                .result(novelService.getAllNovel(containDeleted))
                .build();
    }

    @GetMapping("/get-novel-with-filter")
    ApiResponse<PageResponse<NovelResponse>> getNovelWithFilter(
            @ModelAttribute NovelFilterRequest request
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovelWithFilter(request))
                .build();
    }

    @PostMapping("/{novelId}/commented-novel")
    ApiResponse<NovelResponse> updateCommentTotal(
           @PathVariable String novelId, @ModelAttribute NovelUpdateRequest request
    ){
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.updateNovel(novelId, request))
                .build();
    }

    @GetMapping("/{novelId}/can-be-publish")
    ApiResponse<NovelCanPublishResponse> canBePublish(@PathVariable String novelId) {
        return ApiResponse.<NovelCanPublishResponse>builder()
                .result(novelService.checkNovelCanPublish(novelId))
                .build();
    }

    // ✅ Tạo mới publish request
    @PostMapping("/publish-request")
    public ApiResponse<NovelPublishRequestResponse> createNovelPublishRequest(
            @Valid @RequestBody NovelPublishRequestCreationRequest request
    ) {
        return ApiResponse.<NovelPublishRequestResponse>builder()
                .result(novelPublishRequestService.create(request))
                .build();
    }

    // ✅ Lấy tất cả (không phân trang)
    @GetMapping("/publish-request/all-request")
    public ApiResponse<PageResponse<NovelPublishRequestResponse>> getAll(@ModelAttribute BaseFilterRequest request) {
        return ApiResponse.<PageResponse<NovelPublishRequestResponse>>builder()
                .result(novelPublishRequestService.getAll(request))
                .build();
    }

    // ✅ Lấy theo requestedBy (có phân trang)
//    @GetMapping("/by-user")
//    public ApiResponse<PageResponse<NovelPublishRequestResponse>> getByRequestedBy(
//            @RequestParam String username,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        return ResponseEntity.ok(novelPublishRequestService.getByRequestedBy());
//    }

//    // ✅ Lấy theo status (mặc định là PENDING)
//    @GetMapping("/by-status")
//    public ResponseEntity<PageResponse<NovelPublishRequestResponse>> getByStatus(BaseFilterRequest request) {
//        return ResponseEntity.ok(novelPublishRequestService.getByPublishingStatus(request));
//    }

    // ✅ Lấy theo ID
    @GetMapping("/publish-requested/{id}")
    public ApiResponse<NovelPublishRequestResponse> getById(@PathVariable String id) {
        return ApiResponse.<NovelPublishRequestResponse>builder()
                .result(novelPublishRequestService.getById(id))
                .build();
    }

    // ✅ Xoá theo ID
    @DeleteMapping("/publish-requested/{id}")
    public ApiResponse<Void> deleteById(@PathVariable String id) {
        novelPublishRequestService.deleteById(id);
        return ApiResponse.<Void>builder()
                .build();
    }

    @GetMapping("/publish-request-action")
    public ApiResponse<PageResponse<PublishRequestActionLogResponse>> getAllPublishRequestLog(@ModelAttribute BaseFilterRequest request) {
        return ApiResponse.<PageResponse<PublishRequestActionLogResponse>>builder()
                .result(publishRequestActionLogService.getAll(request))
                .build();
    }

    @GetMapping("/publish-request-action/{id}")
    public ApiResponse<PublishRequestActionLogResponse> getPublishRequestLogById(@PathVariable String id) {
        return ApiResponse.<PublishRequestActionLogResponse>builder()
                .result(publishRequestActionLogService.getById(id))
                .build();
    }

    // ✅ Lấy danh sách đang publish

    //Tajo
    @PostMapping("/publish-request-action")
    public ApiResponse<PublishRequestActionLogResponse> createPublishRequestActionLog(
            @Valid @RequestBody PublishRequestActionLogCreationRequest request
    ) {
        return ApiResponse.<PublishRequestActionLogResponse>builder()
                .result(publishRequestActionLogService.create(request))
                .build();
    }

    @PutMapping("/publish-request-action/{id}")
    public ApiResponse<PublishRequestActionLogResponse> updatePublishRequestActionLog(
            @PathVariable String id,
            @Valid @RequestBody PublishRequestActionLogUpdateRequest request
    ) {
        return ApiResponse.<PublishRequestActionLogResponse>builder()
                .result(publishRequestActionLogService.update(id, request))
                .build();
    }

    /**
     * Lấy danh sách truyện có cùng author (tác giả)
     */
    @GetMapping("/author/{authorId}/novels")
    public ApiResponse<PageResponse<NovelResponse>> getNovelsByAuthor(@PathVariable String authorId, @ModelAttribute NovelFilterRequest request) {
        request.setAuthorId(authorId);
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovelWithFilter(request))
                .build();
    }

//    @GetMapping("/{novelId}/rating")
//    ApiResponse<NovelResponse> newRating(
//            @PathVariable String novelId, @ModelAttribute RatingRequest request
//            ){
//        request.setIsNew(true);
//        return ApiResponse.<NovelResponse>builder()
//                .result(novelService.ratingNovel(novelId, request))
//                .build();
//    }

    @PostMapping("/{novelId}/rating")
    ApiResponse<NovelResponse> updateRating(
            @PathVariable String novelId, @RequestBody RatingRequest request
    ){
//        request.setIsNew(false);
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.ratingNovel(novelId, request))
                .build();
    }

    @DeleteMapping("/{novelId}/rating")
    ApiResponse<NovelResponse> deleteRating(
            @PathVariable String novelId, @RequestBody DeleteRatingRequest request
    ){
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.removeRating(novelId, request))
                .build();
    }

}
