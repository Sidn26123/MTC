package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfActiveRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfGetRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.bookshelf.BookShelfResponse;
import com.sidn.metruyenchu.novelservice.entity.BookShelf;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.BookShelfMapper;
import com.sidn.metruyenchu.novelservice.repository.BookShelfRepository;
import com.sidn.metruyenchu.novelservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookShelfService {
    BookShelfRepository bookShelfRepository;
    BookShelfMapper bookShelfMapper;
    public BookShelfResponse initBookShelf(BookShelfCreateRequest request) {
        BookShelf bookShelf = bookShelfMapper.toEntity(request);
        try {
            bookShelf = bookShelfRepository.save(bookShelf);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
        return bookShelfMapper.toResponse(bookShelf);
    }
    // Create bookshelf
    public BookShelfResponse createBookShelf(BookShelfCreateRequest request) {
        BookShelf bookShelf = bookShelfMapper.toEntity(request);
        bookShelf.setIsActive(false);
        try {
            bookShelf = bookShelfRepository.save(bookShelf);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
        return bookShelfMapper.toResponse(bookShelf);
    }

    // Get bookshelf by ID
    public BookShelfResponse getBookShelf(String bookShelfId) {
        BookShelf bookShelf = bookShelfRepository.findById(bookShelfId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_OF_USER_NOT_FOUND));
        return bookShelfMapper.toResponse(bookShelf);
    }

    // Get all bookshelves of user
    public PageResponse<BookShelfResponse> getAllBookShelvesOfUser(BookShelfGetRequest request) {
        Pageable pageable = PageUtils.from(request);

        var pageData = bookShelfRepository.findAllByUserId(request.getUserId(), pageable);

        return PageUtils.toPageResponse(pageData, bookShelfMapper::toResponse, request.getPage());
    }


    // Update bookshelf
    public BookShelfResponse updateBookShelf(String bookShelfId, BookShelfUpdateRequest request) {
        BookShelf bookShelf = bookShelfRepository.findById(bookShelfId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_OF_USER_NOT_FOUND));

        bookShelfMapper.updateEntity(bookShelf, request);

        try {
            bookShelf = bookShelfRepository.save(bookShelf);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return bookShelfMapper.toResponse(bookShelf);
    }

    // Delete bookshelf (soft delete)
    public void deleteBookShelf(String bookShelfId) {
        BookShelf bookShelf = bookShelfRepository.findById(bookShelfId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_OF_USER_NOT_FOUND));

        bookShelf.setIsDeleted(true);
        try {
            bookShelfRepository.save(bookShelf);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    public void activeBookShelf(BookShelfActiveRequest request){
        List<BookShelf> bookShelfList = bookShelfRepository.findAllByUserId(request.getUserId());

        //Get bookshelf to active
        BookShelf bookShelf = bookShelfList.stream()
                .filter(b -> b.getId().equals(request.getBookShelfId()))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_OF_USER_NOT_FOUND));

        //Set all bookshelves to inactive
        bookShelfList.forEach(b -> b.setIsActive(false));

        //Set the selected bookshelf to active
        bookShelf.setIsActive(true);

        try {
            bookShelfRepository.saveAll(bookShelfList);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }


    }

    public BookShelfResponse getActiveBookShelfOfUser(String userId) {
        List<BookShelf> bookShelfList = bookShelfRepository.findAllByUserId(userId);
        BookShelf bookShelf = bookShelfList.stream()
                .filter(BookShelf::getIsActive)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.BOOKSHELF_OF_USER_NOT_FOUND));

        return bookShelfMapper.toResponse(bookShelf);
    }

    public BookShelf getCurrentActiveBookShelfOfUser(String userId) {
        List<BookShelf> bookShelfList = bookShelfRepository.findAllByUserIdAndIsActiveIsTrue(userId);
        log.info("bookShelfList: {}", bookShelfList.size());
        if (bookShelfList.isEmpty()) {
            log.info("Bookshelf of user {} not found", userId);
            throw new AppException(ErrorCode.BOOKSHELF_OF_USER_NOT_FOUND);
        }

        return bookShelfList.getFirst();
    }
}
