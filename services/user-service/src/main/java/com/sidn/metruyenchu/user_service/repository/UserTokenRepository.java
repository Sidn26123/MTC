package com.sidn.metruyenchu.user_service.repository;

import com.sidn.metruyenchu.user_service.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    Optional<UserToken> findByUserId(String userId);
    List<UserToken> findByUserIdIn(List<String> userIds);
}