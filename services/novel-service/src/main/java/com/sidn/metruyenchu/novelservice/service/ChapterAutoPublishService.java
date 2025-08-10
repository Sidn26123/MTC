package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChapterAutoPublishService {
    
    private final ChapterRepository chapterRepository;
    
    @Transactional
    public void autoPublishChapters() {
        try {
            LocalDateTime now = LocalDateTime.now();
            
            // Tìm các chapter cần publish
            List<Chapter> chaptersToPublish = chapterRepository.findChaptersToPublish(now);
            
            if (chaptersToPublish.isEmpty()) {
                log.debug("Không có chapter nào cần publish tại thời điểm: {}", now);
                return;
            }
            
            log.info("Tìm thấy {} chapter(s) cần publish", chaptersToPublish.size());
            
            // Bulk update để tối ưu performance
            List<String> chapterIds = chaptersToPublish.stream()
                    .map(Chapter::getId)
                    .toList();
                    
            int updatedCount = chapterRepository.bulkUpdatePublishStatus(chapterIds, now);
            
            // Log chi tiết
            chaptersToPublish.forEach(chapter -> 
                log.info("Auto-published chapter: {} (Novel: {}, Scheduled: {})", 
                    chapter.getName(), 
                    chapter.getNovel().getName(),
                    chapter.getPublishedAt())
            );
            
            log.info("Đã auto-publish thành công {} chapter(s)", updatedCount);
            
        } catch (Exception e) {
            log.error("Lỗi khi auto-publish chapters", e);
            throw e;
        }
    }
}