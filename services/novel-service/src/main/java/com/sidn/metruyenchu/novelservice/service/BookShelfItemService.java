package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemGetRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.bookshelfItem.BookShelfItemResponse;
import com.sidn.metruyenchu.novelservice.entity.BookShelf;
import com.sidn.metruyenchu.novelservice.entity.BookShelfItem;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.BookShelfItemMapper;
import com.sidn.metruyenchu.novelservice.repository.BookShelfItemRepository;
import com.sidn.metruyenchu.novelservice.repository.BookShelfRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.utils.PageUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookShelfItemService {
    BookShelfItemRepository bookShelfItemRepository;
    BookShelfItemMapper bookShelfItemMapper;
    BookShelfRepository bookShelfRepository;
    NovelRepository novelRepository;

    // Add item to bookshelf

    /**
     * Thêm item  vào bookshelf, nếu chưa có thì tạo thêm, nếu đã có thì cập nhật lại
     * @param bookShelfId
     * @param request
     * @return
     */
    @Transactional
    public BookShelfItemResponse addItemToBookShelf(String bookShelfId, BookShelfItemCreateRequest request) {
        BookShelfItem item;

        Optional<BookShelfItem> existingItem = bookShelfItemRepository.findByBookShelfIdAndNovelId(bookShelfId, request.getNovelId());

        // Nếu đã có item trong bookshelf thì cập nhật lại
        if (existingItem.isPresent()) {
            item = existingItem.get();
            bookShelfItemMapper.updateEntity(item, request);
//            item.setCurrentChapterIdx(request.getCurrentChapterIdx());
        } else {
            log.info("Creating new item in bookshelf with ID: {}", bookShelfId);
            // Nếu chưa có item trong bookshelf thì tạo mới
            BookShelf bookShelf = bookShelfRepository.findById(bookShelfId)
                    .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_OF_USER_NOT_FOUND));

            Novel novel = novelRepository.findById(request.getNovelId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

            item = bookShelfItemMapper.toEntity(request);
            item.setBookShelf(bookShelf);
            item.setNovel(novel);
        }

        try {
            item = bookShelfItemRepository.save(item);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Duplicate detected, fetching existing item");
            item = bookShelfItemRepository.findByBookShelfIdAndNovelId(bookShelfId, request.getNovelId())
                    .orElseThrow(() -> new AppException(ErrorCode.UNKNOWN_ERROR));
        } catch (Exception ex) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return bookShelfItemMapper.toResponse(item);
    }





    public BookShelfItemResponse getBookShelfItemByNovelId(String bookShelfId, String novelId) {
        BookShelfItem item = bookShelfItemRepository.findByNovelIdAndBookShelfId(novelId, bookShelfId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_ITEM_NOT_FOUND));

        return bookShelfItemMapper.toResponse(item);
    }

    public BookShelfItem getBookShelfItemOfUser(BookShelfItemGetRequest request){
        Optional<BookShelfItem> item = bookShelfItemRepository.findByNovelIdAndBookShelfId(request.getNovelId(), request.getBookShelfId());
        return item.orElse(null);

    }

    // Get item by ID
    public BookShelfItemResponse getBookShelfItemById(String itemId) {
        BookShelfItem item = bookShelfItemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_ITEM_NOT_FOUND));

        return bookShelfItemMapper.toResponse(item);
    }

    /**
     * Delete item from bookshelf (hard delete luôn)
     * @param itemId
     */
    public void deleteBookShelfItemById(String itemId) {
        BookShelfItem item = bookShelfItemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_ITEM_NOT_FOUND));

        try {
            bookShelfItemRepository.delete(item);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    // Delete item from bookshelf (hard delete luôn) với điều kiện là item có novelId
    public void deleteBookShelfItemByNovelId(String bookShelfId, String novelId) {
        BookShelfItem item = bookShelfItemRepository.findByNovelId(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_ITEM_NOT_FOUND));

        try {
            bookShelfItemRepository.delete(item);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    //Lấy tất cả item trong bookshelf có paging
    public PageResponse<BookShelfItemResponse> getBookShelfItemsInBookShelf(String bookShelfId, BookShelfItemGetRequest request) {
        Pageable pageable = PageUtils.from(request);

        var pageData = bookShelfItemRepository.findAllByBookShelfId(bookShelfId, pageable);

        return PageUtils.toPageResponse(pageData, bookShelfItemMapper::toResponse, request.getPage());
    }

    //Lấy tất cả item trong bookshelf dạng list

    /**
     * Lấy tất cả item trong bookshelf
     * @param bookShelfId
     * @return
     */
    public List<BookShelfItemResponse> getAllBookShelfItemsInBookShelf(String bookShelfId) {
        //Lay tất cả item trong bookshelf
        List<BookShelfItem> items = bookShelfItemRepository.findAllByBookShelfId(bookShelfId);

        return items.stream()
                .map(bookShelfItemMapper::toResponse)
                .toList();
    }


    //Lay tất cả item trong bookshelf

    /**
     * Lấy tất cả item trong bookshelf
     * @param bookShelfId
     * @return
     */
    public List<BookShelfItem> getAllBookShelfItemsInBookShelfEntity(String bookShelfId) {

        return bookShelfItemRepository.findAllByBookShelfId(bookShelfId);
    }

    /**
     * Cập nhật lại item trong bookshelf
     * @param itemId
     * @param request
     * @return
     */
    public BookShelfItemResponse updateBookShelfItem(String itemId, @Valid BookShelfItemUpdateRequest request) {
        BookShelfItem item = bookShelfItemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_ITEM_NOT_FOUND));

        bookShelfItemMapper.updateEntity(item, request);

        item = bookShelfItemRepository.save(item);

        return bookShelfItemMapper.toResponse(item);
    }

    public PageResponse<BookShelfItemResponse> getBookShelfItemsInActiveBookShelf(String userId, BookShelfItemGetRequest request) {
        List<BookShelf> bookShelfList = bookShelfRepository.findAllByUserId(userId);
        BookShelf bookShelf = bookShelfList.stream()
                .filter(BookShelf::getIsActive)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_OF_USER_NOT_FOUND));

        Pageable pageable = PageUtils.from(request);

        var pageData = bookShelfItemRepository.findAllByBookShelfId(bookShelf.getId(), pageable);

        return null;
    }
}
