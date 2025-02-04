package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.NovelAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelAuthorRepository extends JpaRepository<NovelAuthor, String> {
}
