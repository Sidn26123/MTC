package com.sidn.metruyenchu.paymentservice.repository;


import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {
    Optional<Coupon> findByCodeAndStatus(String code, CouponStatus status);

    @Query("SELECT c FROM Coupon c WHERE c.status = :status AND c.validFrom <= :now AND c.validUntil >= :now")
    List<Coupon> findActiveCoupons(@Param("status") CouponStatus status, @Param("now") LocalDateTime now);

    @Query("SELECT c FROM Coupon c WHERE c.validUntil < :now AND c.status = :status")
    List<Coupon> findExpiredCoupons(@Param("now") LocalDateTime now, @Param("status") CouponStatus status);

    Optional<Coupon> findByCode(String couponCode);

}
