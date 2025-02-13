package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.NovelAuthor;
import com.sidn.metruyenchu.novelservice.entity.NovelSect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelSectRepository extends JpaRepository<NovelSect, String> {
}
