package com.sidn.metruyenchu.paymentservice.repository;
import com.sidn.metruyenchu.paymentservice.entity.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, String> {
    List<CouponUsage> findByUserId(String userId);

    @Query("SELECT cu FROM CouponUsage cu WHERE cu.coupon.code = :couponCode")
    Integer countCouponUsageValid(String couponCode);
}
