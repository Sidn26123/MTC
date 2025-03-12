package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Rating, String> {
}
