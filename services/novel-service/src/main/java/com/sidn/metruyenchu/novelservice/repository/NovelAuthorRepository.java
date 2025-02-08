package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.NovelAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NovelAuthorRepository extends JpaRepository<NovelAuthor, String> {
    boolean existsByName(String name);
    Optional<NovelAuthor> findByName(String name);
    List<NovelAuthor> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);


}
