package com.sidn.metruyenchu.notificationservice.repository;

import com.sidn.metruyenchu.notificationservice.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    Optional<UserToken> findByUserId(String userId);
    List<UserToken> findByUserIdIn(List<String> userIds);
    @Query("SELECT dt.fcmToken FROM UserToken dt WHERE dt.userId = :userId AND dt.isActive = true")
    List<String> findActiveTokensByUserId(@Param("userId") String userId);

    Optional<UserToken> findByFcmToken(String token);

    void deleteByUserIdAndFcmToken(String userId, String token);
}