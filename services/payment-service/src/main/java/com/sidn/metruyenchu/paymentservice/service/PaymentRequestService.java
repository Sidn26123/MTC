package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentRequest.PaymentRequestCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentRequest.PaymentRequestUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentRequest.PaymentRequestResponse;
import com.sidn.metruyenchu.paymentservice.entity.PaymentMethod;
import com.sidn.metruyenchu.paymentservice.entity.PaymentRequest;
import com.sidn.metruyenchu.shared_library.enums.payment.PaymentMethodStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.PaymentRequestStatus;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import com.sidn.metruyenchu.paymentservice.mapper.PaymentMethodMapper;
import com.sidn.metruyenchu.paymentservice.mapper.PaymentRequestsMapper;
import com.sidn.metruyenchu.paymentservice.repository.PaymentMethodRepository;
import com.sidn.metruyenchu.paymentservice.repository.PaymentRequestRepository;
import com.sidn.metruyenchu.paymentservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentRequestService {

    PaymentRequestRepository paymentRequestRepository;
    PaymentRequestsMapper paymentRequestsMapper;
    PaymentMethodRepository paymentMethodRepository;

    public PaymentRequestResponse createPaymentRequest(PaymentRequestCreateRequest request) {
        // Lấy phương thức thanh toán
        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

        PaymentRequest entity = paymentRequestsMapper.toEntity(request);
        entity.setPaymentMethod(paymentMethod);
        entity.setRequestCode(UUID.randomUUID().toString()); // Sinh mã yêu cầu duy nhất
        entity.setStatus(PaymentRequestStatus.PENDING); // Mặc định là PENDING
        try {
            entity = paymentRequestRepository.save(entity);
        } catch (Exception e) {
            log.error("Error while saving PaymentRequest: {}", e.getMessage());
            throw new AppException(ErrorCode.PAYMENT_REQUEST_FAILED);
        }

        return paymentRequestsMapper.toResponse(entity);
    }

    public PaymentRequestResponse getPaymentRequestById(String id) {
        PaymentRequest request = paymentRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_REQUEST_NOT_FOUND));

        return paymentRequestsMapper.toResponse(request);
    }

    public List<PaymentRequestResponse> getAllPaymentRequests() {
        List<PaymentRequest> requests = paymentRequestRepository.findAll();
        return paymentRequestsMapper.toResponses(requests);
    }

    public PaymentRequestResponse updatePaymentRequest(String id, PaymentRequestUpdateRequest request) {
        PaymentRequest paymentRequest = paymentRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_REQUEST_NOT_FOUND));


        paymentRequestsMapper.updateEntity(paymentRequest, request);

        // Nếu có cập nhật paymentMethodId, cần nạp lại PaymentMethod
//        PaymentMethod newMethod = paymentMethodRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));
//
//
//        paymentRequest.setPaymentMethod(newMethod);

        return paymentRequestsMapper.toResponse(paymentRequestRepository.save(paymentRequest));
    }

    public void hardDeletePaymentRequest(String id) {
        PaymentRequest paymentRequest = paymentRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_REQUEST_NOT_FOUND));

        paymentRequestRepository.delete(paymentRequest);
    }

    public List<BigDecimal> getMonthlyRevenue(int year) {
        // Lấy dữ liệu từ DB
        List<Object[]> rawData = paymentRequestRepository.getMonthlyRevenue(year);

        // Khởi tạo danh sách 12 tháng = 0
        List<BigDecimal> monthlyRevenue = new ArrayList<>(Collections.nCopies(12, BigDecimal.ZERO));

        // Gán dữ liệu từ DB vào đúng tháng (tháng trong DB = 1..12)
        for (Object[] row : rawData) {
            Integer month = ((Number) row[0]).intValue();
            BigDecimal total = (BigDecimal) row[1];
            monthlyRevenue.set(month - 1, total);
        }

        return monthlyRevenue;
    }
}
