package com.sidn.metruyenchu.paymentservice.repository;
import com.sidn.metruyenchu.paymentservice.entity.UserCoupon;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.UserCouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, String> {
//    Optional<UserCoupon> findByUserIdAndCouponId(String userId, String couponId);
//    List<UserCoupon> findByUserId(String userId);
//
//    List<UserCoupon> findByUserIdAndStatusAndCouponValidUntilAfter(String userId, UserCouponStatus userCouponStatus, LocalDateTime now);
//
//    Optional<UserCoupon> findByUserIdAndCouponCode(String userId, String couponCode);

    Optional<UserCoupon> findByUserIdAndCouponId(String userId, String couponId);

    @Query("SELECT uc FROM UserCoupon uc JOIN uc.coupon c WHERE uc.userId = :userId AND c.code = :code")
    Optional<UserCoupon> findByUserIdAndCouponCode(@Param("userId") String userId, @Param("code") String code);

    List<UserCoupon> findByUserIdAndStatusAndCouponValidUntilAfter(
            String userId, UserCouponStatus status, LocalDateTime validUntil);

    @Query("SELECT uc FROM UserCoupon uc JOIN uc.coupon c WHERE uc.userId = :userId " +
            "AND uc.status = :status AND c.validUntil >= :now " +
            "AND (c.applicableType = :applicableType OR c.applicableType = 'ALL')")
    List<UserCoupon> findAvailableCouponsByType(@Param("userId") String userId,
                                                @Param("status") UserCouponStatus status,
                                                @Param("now") LocalDateTime now,
                                                @Param("applicableType") CouponApplicableType applicableType);
}
