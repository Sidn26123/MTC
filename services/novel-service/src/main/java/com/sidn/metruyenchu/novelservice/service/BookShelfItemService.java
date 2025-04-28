package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemCreateRequest;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public BookShelfItemResponse addItemToBookShelf(String bookShelfId, BookShelfItemCreateRequest request) {
        //Kiem tra xem item đã tồn tại hay chưa
        BookShelf bookShelf = bookShelfRepository.findById(bookShelfId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_ITEM_NOT_FOUND));

        //Kiem tra xem novel đã tồn tại hay chưa
        Novel novel = novelRepository.findById(request.getNovelId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

//        BookShelfItem item = BookShelfItem.builder()
//                .bookShelf(bookShelf)
//                .novel(novel)
//                .build();

        BookShelfItem item = bookShelfItemMapper.toEntity(request);
        item.setBookShelf(bookShelf);
        item.setNovel(novel);


        try {
            item = bookShelfItemRepository.save(item);
        } catch (Exception e) {
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
}
