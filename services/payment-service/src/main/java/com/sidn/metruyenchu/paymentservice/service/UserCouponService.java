package com.sidn.metruyenchu.paymentservice.service;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.UserCouponRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.UserCouponUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.UserCouponResponse;
import com.sidn.metruyenchu.paymentservice.entity.UserCoupon;
import com.sidn.metruyenchu.paymentservice.mapper.UserCouponMapper;
import com.sidn.metruyenchu.paymentservice.repository.UserCouponRepository;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCouponService {
    UserCouponRepository repository;
    UserCouponMapper mapper;

    public UserCouponResponse create(UserCouponRequest request) {
        UserCoupon entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public UserCouponResponse update(String id, UserCouponUpdateRequest request) {
        UserCoupon entity = repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        mapper.updateFromRequest(request, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public UserCouponResponse getById(String id) {
        return mapper.toResponse(repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)));
    }

    public List<UserCouponResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }
}
