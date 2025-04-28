package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelFilterRequest;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelRepository extends JpaRepository<Novel, String>, JpaSpecificationExecutor<Novel> {
    Novel findByName(String name);

    Optional<Novel> findBySlug(String slug);

    Page<Novel> findByNameContaining(String keyword, Pageable pageable);

    Optional<Novel> findByAuthorId(String authorId);


//    @Query("SELECT n FROM Novel n WHERE n.name LIKE %:searchText% OR n.author.name LIKE %:searchText% AND n.isDeleted = false")
//    Page<Novel> findByFilter(NovelFilterRequest request, Pageable pageable);

    @Query("select gettotalnovel(?1)")
    int getAllNovel(boolean containDeleted);

    @Query(value = """
    SELECT * FROM get_filtered_novels(
        :#{#filter.genres != null ? T(java.lang.String).join(',', #filter.genres) : null},
        :#{#filter.worldScenes != null ? T(java.lang.String).join(',', #filter.worldScenes) : null},
        :#{#filter.status != null ? T(java.lang.String).join(',', #filter.status) : null},
        :#{#filter.sects != null ? T(java.lang.String).join(',', #filter.sects) : null},
        :#{#filter.mainCharacterTraits != null ? T(java.lang.String).join(',', #filter.mainCharacterTraits) : null},
        :#{#filter.sortBy ?: 'created_at'},
        :#{#filter.sortOrder ?: 'DESC'}
    )
    """, nativeQuery = true)
    Page<Novel> findNovelWithFilter(@Param("filter") NovelFilterRequest request, Pageable pageable);

    @Modifying
    @Query("UPDATE Novel n SET n.chapterReadToComment = :value WHERE n.id = :novelId")
    void updateChapterReadToComment(@Param("novelId") String novelId, @Param("value") Integer value);

    @Modifying
    @Query("UPDATE Novel n SET n.chapterReadToRate = :value WHERE n.id = :novelId")
    void updateChapterReadToRate(@Param("novelId") String novelId, @Param("value") Integer value);

    @Modifying
    @Query("UPDATE Novel n SET n.fullSetPurchaseDiscount = :value WHERE n.id = :novelId")
    void updateFullSetPurchaseDiscount(@Param("novelId") String novelId, @Param("value") Integer value);

    @Modifying
    @Query("UPDATE Novel n SET n.wordCount = :value WHERE n.id = :novelId")
    void updateWordCount(@Param("novelId") String novelId, @Param("value") Integer value);

    // --- totalComments ---
    @Modifying
    @Query("UPDATE Novel n SET n.totalComments = n.totalComments + 1 WHERE n.id = :novelId")
    void incrementTotalComments(@Param("novelId") String novelId);

    @Modifying
    @Query("UPDATE Novel n SET n.totalComments = n.totalComments - 1 WHERE n.id = :novelId AND n.totalComments > 0")
    void decrementTotalComments(@Param("novelId") String novelId);

    // --- totalRates ---
    @Modifying
    @Query("UPDATE Novel n SET n.totalRates = n.totalRates + 1 WHERE n.id = :novelId")
    void incrementTotalRates(@Param("novelId") String novelId);

    @Modifying
    @Query("UPDATE Novel n SET n.totalRates = n.totalRates - 1 WHERE n.id = :novelId AND n.totalRates > 0")
    void decrementTotalRates(@Param("novelId") String novelId);

    // --- avgRate ---
    @Modifying
    @Query("UPDATE Novel n SET n.avgRate = :rate WHERE n.id = :novelId")
    void updateAvgRate(@Param("novelId") String novelId, @Param("rate") Float rate);

    @Modifying
    @Query("UPDATE Novel n SET n.totalChapters = :totalChapters WHERE n.id = :novelId")
    void updateTotalChapters(@Param("novelId") String novelId, @Param("totalChapters") int totalChapters);

}
