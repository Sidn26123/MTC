package com.sidn.metruyenchu.notificationservice.repository;

import com.sidn.metruyenchu.notificationservice.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByRecipientIdAndIsArchivedFalse(String recipientId);
    Page<Notification> findByRecipientIdAndIsArchivedFalse(String recipientId, Pageable pageable);

    Page<Notification> findByRecipientIdAndIsReadFalse(String userId, Pageable pageable);
    List<Notification> findByRecipientIdAndIsReadFalse(String recipientId);

    Page<Notification> findByRecipientIdAndIsReadFalseAndIsArchivedFalse(String userId, Pageable pageable);

    long countByRecipientIdAndIsReadFalse(String userId);

    Optional<Notification> findByIdAndRecipientId(String notificationId, String userId);
}
