package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemGetRequest;
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

        if (existingItem.isPresent()) {
            item = existingItem.get();
            bookShelfItemMapper.updateEntity(item, request);
//            item.setCurrentChapterIdx(request.getCurrentChapterIdx());
        } else {
            log.info("Item not found");

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





    // Get item by ID
    public BookShelfItemResponse getBookShelfItem(String bookShelfId, String itemId) {
        BookShelfItem item = bookShelfItemRepository.findById(itemId)
                .filter(i -> i.getBookShelf().getId().equals(bookShelfId))
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_ITEM_NOT_FOUND));

        return bookShelfItemMapper.toResponse(item);
    }

    // Delete item from bookshelf (hard delete luôn)
    public void deleteBookShelfItem(String bookShelfId, String itemId) {
        BookShelfItem item = bookShelfItemRepository.findById(itemId)
                .filter(i -> i.getBookShelf().getId().equals(bookShelfId))
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
    public List<BookShelfItemResponse> getAllBookShelfItemsInBookShelf(String bookShelfId) {
        //Lay tất cả item trong bookshelf
        List<BookShelfItem> items = bookShelfItemRepository.findAllByBookShelfId(bookShelfId);

        return items.stream()
                .map(bookShelfItemMapper::toResponse)
                .toList();
    }


    //Lay tất cả item trong bookshelf
    public List<BookShelfItem> getAllBookShelfItemsInBookShelfEntity(String bookShelfId) {

        return bookShelfItemRepository.findAllByBookShelfId(bookShelfId);
    }


}
