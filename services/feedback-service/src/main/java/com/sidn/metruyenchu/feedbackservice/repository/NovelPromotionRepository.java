package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.NovelPromotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelPromotionRepository extends JpaRepository<NovelPromotion, String> {
    Page<NovelPromotion> findByNovelId(String novelId, Pageable pageable);

}
