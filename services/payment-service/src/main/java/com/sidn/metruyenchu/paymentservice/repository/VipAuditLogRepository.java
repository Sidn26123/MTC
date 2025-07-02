package com.sidn.metruyenchu.paymentservice.repository;

import com.sidn.metruyenchu.paymentservice.entity.VipAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VipAuditLogRepository extends JpaRepository<VipAuditLog, String> {
    
    List<VipAuditLog> findByUserIdOrderByCreatedAtDesc(String userId);
    
    List<VipAuditLog> findByUserIdAndActionOrderByCreatedAtDesc(String userId, String action);
    
    @Query("SELECT val FROM VipAuditLog val WHERE val.userId = :userId " +
           "AND val.createdAt BETWEEN :from AND :to ORDER BY val.createdAt DESC")
    List<VipAuditLog> findByUserIdAndDateRange(@Param("userId") String userId,
                                              @Param("from") LocalDateTime from,
                                              @Param("to") LocalDateTime to);
}