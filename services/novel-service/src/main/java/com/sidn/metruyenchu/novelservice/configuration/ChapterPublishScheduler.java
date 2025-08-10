package com.sidn.metruyenchu.novelservice.configuration;

import com.sidn.metruyenchu.novelservice.service.ChapterAutoPublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChapterPublishScheduler {
    
    private final ChapterAutoPublishService autoPublishService;
    
    // Chạy mỗi 5 phút
    @Scheduled(cron = "0 */5 * * * *")
    public void autoPublishChapters() {
        log.debug("Bắt đầu kiểm tra auto-publish chapters...");
        autoPublishService.autoPublishChapters();
    }
    
    // Hoặc có thể chạy mỗi phút nếu cần độ chính xác cao hơn
    // @Scheduled(cron = "0 * * * * *")
    
    // Hoặc chạy mỗi 15 phút để giảm tải
    // @Scheduled(cron = "0 */15 * * * *")
}