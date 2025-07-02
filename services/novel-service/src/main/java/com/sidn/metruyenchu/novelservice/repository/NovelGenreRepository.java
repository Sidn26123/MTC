package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.NovelAuthor;
import com.sidn.metruyenchu.novelservice.entity.NovelGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelGenreRepository extends JpaRepository<NovelGenre, String> {
}
