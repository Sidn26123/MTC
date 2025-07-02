package com.sidn.metruyenchu.paymentservice.repository;
import com.sidn.metruyenchu.paymentservice.entity.VipMembership;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.VipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface VipMembershipRepository extends JpaRepository<VipMembership, String> {
    List<VipMembership> findByUserId(String userId);
    Optional<VipMembership> findByUserIdAndStatus(String userId, VipStatus status);

    Optional<VipMembership> findByUserIdAndStatusAndEndDateAfter(String userId, VipStatus vipStatus, LocalDateTime now);
    List<VipMembership> findByStatusAndEndDateAfter(VipStatus vipStatus, LocalDateTime now);
    List<VipMembership> findByExpiredAtBeforeAndStatus(LocalDateTime now, VipStatus vipStatus);
}
