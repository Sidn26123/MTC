package com.sidn.metruyenchu.paymentservice.repository;

import com.sidn.metruyenchu.paymentservice.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, String> {
//    @Query("""
//    SELECT MONTH(pr.completedAt),
//           COALESCE(SUM(pr.amount), 0)
//    FROM PaymentRequest pr
//    WHERE YEAR(pr.completedAt) = :year
//      AND pr.status = 'COMPLETED'
//    GROUP BY MONTH(pr.completedAt)
//    ORDER BY MONTH(pr.completedAt)
//""")
//    @Query(value = """
//        SELECT EXTRACT(MONTH FROM completed_at) AS month,
//               COALESCE(SUM(amount), 0) AS total
//        FROM payment_request
//        WHERE EXTRACT(YEAR FROM completed_at) = :year
//          AND status = 'COMPLETED'
//        GROUP BY month
//        ORDER BY month
//    """, nativeQuery = true)
@Query("""
    SELECT MONTH(pr.completedAt),
           COALESCE(SUM(pr.amount), 0)
    FROM PaymentRequest pr
    WHERE YEAR(pr.completedAt) = :year
      AND pr.status = 'COMPLETED'
    GROUP BY MONTH(pr.completedAt)
    ORDER BY MONTH(pr.completedAt)
""")
    List<Object[]> getMonthlyRevenue(@Param("year") int year);
}
