package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentRequest.PaymentRequestCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentRequest.PaymentRequestUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentRequest.PaymentRequestResponse;
import com.sidn.metruyenchu.paymentservice.entity.PaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
@Mapper(componentModel = "spring",
         nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = {PaymentMethodMapper.class})
public interface PaymentRequestsMapper {
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    PaymentRequestResponse toResponse(PaymentRequest paymentRequests);

    PaymentRequest toEntity(PaymentRequestCreateRequest request);

    List<PaymentRequestResponse> toResponses(List<PaymentRequest> paymentRequests);

    void updateEntity(@MappingTarget PaymentRequest paymentRequests, PaymentRequestUpdateRequest request);
}