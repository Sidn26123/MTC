package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.BookShelfItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookShelfItemRepository extends JpaRepository<BookShelfItem, String> {
    Optional<BookShelfItem> findByNovelIdAndBookShelfId(String novelId, String bookShelfId);
    void deleteByNovelIdAndBookShelfId(String novelId, String bookShelfId);

    List<BookShelfItem> findAllByBookShelfId(String bookShelfId);
    Page<BookShelfItem> findAllByBookShelfId(String bookShelfId, Pageable pageable);

    boolean existsByIdAndBookShelfId(String itemId, String bookShelfId);
    Optional<BookShelfItem> findByBookShelfIdAndNovelId(String bookShelfId, String novelId);

    Optional<BookShelfItem> findByNovelId(String novelId);
}
