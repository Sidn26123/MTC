package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.BookShelfItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookShelfItemRepository extends JpaRepository<BookShelfItem, String> {
    BookShelfItem findByNovelIdAndBookShelfId(String novelId, String bookShelfId);
    void deleteByNovelIdAndBookShelfId(String novelId, String bookShelfId);
}
