package com.sidn.metruyenchu.paymentservice.repository;
import com.sidn.metruyenchu.paymentservice.entity.VipPlan;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface VipPlanRepository extends JpaRepository<VipPlan, String> {
//    List<VipPlan> findByActiveTrueOrderBySortOrder();
    Page<VipPlan> findByActiveTrueOrderBySortOrder(Pageable pageable);

    Optional<VipPlan> findByIdAndActiveTrue(String id);

    Optional<VipPlan> findByNameAndActiveTrue(String name);}
