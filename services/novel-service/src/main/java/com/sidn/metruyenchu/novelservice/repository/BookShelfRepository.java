package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.entity.BookShelf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookShelfRepository extends JpaRepository<BookShelf, String> {
    List<BookShelf> findAllByUserId(String userId);
    Page<BookShelf> findAllByUserId(String userId, Pageable pageable);
    List<BookShelf> findAllByUserIdAndIsActiveIsTrue(String userId);
}
