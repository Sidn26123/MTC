package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfActiveRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfGetRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemGetRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.bookshelf.BookShelfResponse;
import com.sidn.metruyenchu.novelservice.dto.response.bookshelfItem.BookShelfItemResponse;
import com.sidn.metruyenchu.novelservice.service.BookShelfItemService;
import com.sidn.metruyenchu.novelservice.service.BookShelfService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookshelfs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookShelfController {
    BookShelfService bookShelfService;
    BookShelfItemService bookShelfItemService;

    /**
     * Được gọi khi user onboard lần đầu tiên
     * @param request
     * @return
     */
    @PostMapping("/init")
    public ApiResponse<BookShelfResponse> initBookShelf(@Valid @RequestBody BookShelfCreateRequest request) {
        return ApiResponse.<BookShelfResponse>builder()
                .result(bookShelfService.initBookShelf(request))
                .build();
    }

    @PostMapping
    public ApiResponse<BookShelfResponse> createBookShelf(@Valid @RequestBody BookShelfCreateRequest request) {
        return ApiResponse.<BookShelfResponse>builder()
                .result(bookShelfService.createBookShelf(request))
                .build();
    }

    @GetMapping("/{bookShelfId}")
    public ApiResponse<BookShelfResponse> getBookShelf(@PathVariable String bookShelfId) {
        return ApiResponse.<BookShelfResponse>builder()
                .result(bookShelfService.getBookShelf(bookShelfId))
                .build();
    }

    /**
     * Get all bookshelves of user
     * @param request BookShelfGetRequest
     * @return
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<PageResponse<BookShelfResponse>> getAllBookShelfOfUser(@PathVariable String userId, @RequestBody BookShelfGetRequest request) {
        request.setUserId(userId);

        return ApiResponse.<PageResponse<BookShelfResponse>>builder()
                .result(bookShelfService.getAllBookShelvesOfUser(request))
                .build();
    }

    //Get current active bookshelf of user
    @GetMapping("/user/{userId}/active")
    public ApiResponse<BookShelfResponse> getActiveBookShelfOfUser(@PathVariable String userId) {
        return ApiResponse.<BookShelfResponse>builder()
                .result(bookShelfService.getActiveBookShelfOfUser(userId))
                .build();
    }

    //Get item of current active bookshelf of user
    @GetMapping("/user/{userId}/active/items")
    public ApiResponse<PageResponse<BookShelfItemResponse>> getItemsInActiveBookShelfOfUser(@PathVariable String userId,
                                                                                               @ModelAttribute BookShelfItemGetRequest request) {
        return ApiResponse.<PageResponse<BookShelfItemResponse>>builder()
                .result(bookShelfItemService.getBookShelfItemsInActiveBookShelf(userId, request))
                .build();
    }


    @PutMapping("/{bookShelfId}")
    public ApiResponse<BookShelfResponse> updateBookShelf(@PathVariable String bookShelfId,
                                                          @Valid @RequestBody BookShelfUpdateRequest request) {
        return ApiResponse.<BookShelfResponse>builder()
                .result(bookShelfService.updateBookShelf(bookShelfId, request))
                .build();
    }

    @DeleteMapping("/{bookShelfId}")
    public ApiResponse<String> deleteBookShelf(@PathVariable String bookShelfId) {
        bookShelfService.deleteBookShelf(bookShelfId);
        return ApiResponse.<String>builder()
                .result(String.format("Bookshelf %s đã xoá thành công", bookShelfId))
                .build();
    }

    // --- BookShelfItem CRUD ---

    @GetMapping("/{bookShelfId}/items")
    public ApiResponse<PageResponse<BookShelfItemResponse>> getItemsInBookShelf(@PathVariable String bookShelfId,
                                                                 @Valid @ModelAttribute BookShelfItemGetRequest request) {
        return ApiResponse.<PageResponse<BookShelfItemResponse>>builder()
                .result(bookShelfItemService.getBookShelfItemsInBookShelf(bookShelfId, request))
                .build();
    }

    @PostMapping("/{bookShelfId}/items")
    public ApiResponse<BookShelfItemResponse> addItemToBookShelf(@PathVariable String bookShelfId,
                                                                 @Valid @RequestBody BookShelfItemCreateRequest request) {
        request.setBookShelfId(bookShelfId);

        return ApiResponse.<BookShelfItemResponse>builder()
                .result(bookShelfItemService.addItemToBookShelf(bookShelfId, request))
                .build();
    }

    /**
     * Cập nhật lại item trong bookshelf
     * @param itemId UUID
     * @param request BookShelfItemUpdateRequest
     * @return
     */
    @PutMapping("/items/{itemId}")
    public ApiResponse<BookShelfItemResponse> updateBookShelfItem(@PathVariable String itemId,
                                                              @Valid @RequestBody BookShelfItemUpdateRequest request) {
        return ApiResponse.<BookShelfItemResponse>builder()
                .result(bookShelfItemService.updateBookShelfItem(itemId, request))
                .build();
    }

    /**
     * Lấy item trong bookshelf có novel id
     * @param bookShelfId
     * @param novelId
     * @return
     */
    @GetMapping("/{bookShelfId}/items/{novelId}")
    public ApiResponse<BookShelfItemResponse> getBookShelfItem(@PathVariable String bookShelfId,
                                                               @PathVariable String novelId) {
        return ApiResponse.<BookShelfItemResponse>builder()
                .result(bookShelfItemService.getBookShelfItemByNovelId(bookShelfId, novelId))
                .build();
    }

    @GetMapping("/items/{itemId}")
    public ApiResponse<BookShelfItemResponse> getBookShelfItem(@PathVariable String itemId) {
        return ApiResponse.<BookShelfItemResponse>builder()
                .result(bookShelfItemService.getBookShelfItemById(itemId))
                .build();
    }

    @DeleteMapping("/items/{itemId}")
    public ApiResponse<String> deleteBookShelfItem(@PathVariable String itemId) {
        bookShelfItemService.deleteBookShelfItemById(itemId);
        return ApiResponse.<String>builder()
                .result(String.format("Item %s đã xoá thành công khỏi Bookshelf", itemId))
                .build();
    }

    /**
     * Delete item from bookshelf which have novel id
     * @param bookShelfId
     * @param novelId
     * @return
     */
    @DeleteMapping("/{bookShelfId}/items/{novelId}")
    public ApiResponse<String> deleteBookShelfItemByNovelId(@PathVariable String bookShelfId,
                                                   @PathVariable String novelId) {
        bookShelfItemService.deleteBookShelfItemByNovelId(bookShelfId, novelId);
        return ApiResponse.<String>builder()
                .result(String.format("Item %s đã xoá thành công khỏi Bookshelf %s", novelId, bookShelfId))
                .build();
    }

    /**
     * Kích hoạt bookshelf, mỗi user có thể có nhiều bookshelf nhưng chỉ có 1 bookshelf được kích hoạt
     * @param request BookShelfActiveRequest
     * @return String
     */
    @PostMapping("/active")
    public ApiResponse<String> activeBookShelf(@Valid @RequestBody BookShelfActiveRequest request) {
        bookShelfService.activeBookShelf(request);
        return ApiResponse.<String>builder()
                .result(String.format("Bookshelf %s đã kích hoạt thành công", request.getUserId()))
                .build();
    }
}
