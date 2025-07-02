package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod.PaymentMethodCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.payment.paymentMethod.PaymentMethodUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.paymentMethod.PaymentMethodResponse;
import com.sidn.metruyenchu.paymentservice.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMethodMapper {
    PaymentMethod toEntity(PaymentMethodCreateRequest request);

    PaymentMethodResponse toResponse(PaymentMethod paymentMethods);

    List<PaymentMethodResponse> toResponses(List<PaymentMethod> paymentMethods);

    void updateEntity(@MappingTarget PaymentMethod paymentMethods, PaymentMethodUpdateRequest request);
}