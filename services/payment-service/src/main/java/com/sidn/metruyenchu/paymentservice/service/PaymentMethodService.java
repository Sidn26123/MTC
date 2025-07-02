package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod.PaymentMethodCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod.PaymentMethodUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentMethod.PaymentMethodResponse;
import com.sidn.metruyenchu.paymentservice.entity.PaymentMethod;
import com.sidn.metruyenchu.paymentservice.enums.PaymentMethodStatus;
import com.sidn.metruyenchu.paymentservice.exception.AppException;
import com.sidn.metruyenchu.paymentservice.exception.ErrorCode;
import com.sidn.metruyenchu.paymentservice.mapper.PaymentMethodMapper;
import com.sidn.metruyenchu.paymentservice.repository.PaymentMethodRepository;
import com.sidn.metruyenchu.paymentservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentMethodService {

    PaymentMethodRepository paymentMethodRepository;
    PaymentMethodMapper paymentMethodMapper;

    public PaymentMethodResponse createPaymentMethod(PaymentMethodCreateRequest request) {
        PaymentMethod entity = paymentMethodMapper.toEntity(request);
        entity.setStatus(PaymentMethodStatus.ACTIVE);
        log.info(String.valueOf(entity.getStatus()));
        try {
            entity = paymentMethodRepository.save(entity);
        } catch (Exception e) {
            log.error("Error while saving PaymentMethod: {}", e.getMessage());
            throw new AppException(ErrorCode.PAYMENT_METHOD_ALREADY_EXISTS);
        }

        return paymentMethodMapper.toResponse(entity);
    }

    public PaymentMethodResponse getPaymentMethodById(String id) {
        PaymentMethod method = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

        return paymentMethodMapper.toResponse(method);
    }

    public PageResponse<PaymentMethodResponse> getAllPaymentMethods(BaseFilterRequest request) {
        Pageable pageable = PageUtils.from(request);

        Page<PaymentMethod> pageData = paymentMethodRepository.findAllByStatus(
                PaymentMethodStatus.ACTIVE, pageable);

        return PageUtils.toPageResponse(
                pageData,
                paymentMethodMapper::toResponse,
                request.getPage()
        );
    }

    public PaymentMethodResponse updatePaymentMethod(String id, PaymentMethodUpdateRequest request) {
        PaymentMethod method = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

        paymentMethodMapper.updateEntity(method, request);

        return paymentMethodMapper.toResponse(paymentMethodRepository.save(method));
    }

    public void hardDeletePaymentMethod(String id) {
        PaymentMethod method = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

        paymentMethodRepository.delete(method);
    }
}
