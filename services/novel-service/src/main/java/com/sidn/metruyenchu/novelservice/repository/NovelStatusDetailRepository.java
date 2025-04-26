package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import com.sidn.metruyenchu.novelservice.entity.NovelStatusDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface NovelStatusDetailRepository extends JpaRepository<NovelStatusDetail, String> {
    NovelStatusDetail findByNovel(Novel novel);
    NovelStatusDetail findByNovelId(String novelId);
    NovelStatusDetail findByNovelStatus(NovelStatus novelStatus);

//    List<NovelStatusDetail> findByIdIn(List<String> list);
}
