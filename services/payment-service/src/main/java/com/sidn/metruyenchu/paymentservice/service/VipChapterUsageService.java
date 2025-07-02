package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipChapterUsageRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipChapterUsageUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.vip.VipChapterUsageResponse;
import com.sidn.metruyenchu.paymentservice.entity.VipChapterUsage;
import com.sidn.metruyenchu.paymentservice.mapper.VipChapterUsageMapper;
import com.sidn.metruyenchu.paymentservice.repository.VipChapterUsageRepository;
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
public class VipChapterUsageService {
    VipChapterUsageRepository repository;
    VipChapterUsageMapper mapper;

    public VipChapterUsageResponse create(VipChapterUsageRequest request) {
        VipChapterUsage entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public VipChapterUsageResponse update(String id, VipChapterUsageUpdateRequest request) {
        VipChapterUsage entity = repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        mapper.update(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    public VipChapterUsageResponse getById(String id) {
        return mapper.toResponse(repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)));
    }

    public List<VipChapterUsageResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }
}
