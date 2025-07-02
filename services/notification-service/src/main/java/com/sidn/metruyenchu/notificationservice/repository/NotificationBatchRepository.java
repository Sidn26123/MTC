package com.sidn.metruyenchu.notificationservice.repository;

import com.sidn.metruyenchu.notificationservice.entity.NotificationBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationBatchRepository extends JpaRepository<NotificationBatch, String> {
}
