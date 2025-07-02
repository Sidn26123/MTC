package com.sidn.metruyenchu.paymentservice.repository;
import com.sidn.metruyenchu.paymentservice.entity.VipChapterUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface VipChapterUsageRepository extends JpaRepository<VipChapterUsage, String> {
    @Query("SELECT COUNT(vcu) FROM VipChapterUsage vcu WHERE vcu.userId = :userId AND vcu.yearMonth = :yearMonth")
    long countByUserIdAndYearMonth(@Param("userId") String userId, @Param("yearMonth") String yearMonth);

    List<VipChapterUsage> findByUserIdAndYearMonthOrderByReadAtDesc(String userId, String yearMonth);

    @Query("SELECT vcu.storyId, COUNT(vcu) FROM VipChapterUsage vcu " +
            "WHERE vcu.userId = :userId AND vcu.yearMonth = :yearMonth GROUP BY vcu.storyId")
    List<Object[]> getReadingStatsByUserAndMonth(@Param("userId") String userId, @Param("yearMonth") String yearMonth);}
