package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.currency.CurrencyCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.currency.CurrencyUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.currency.CurrencyResponse;
import com.sidn.metruyenchu.paymentservice.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CurrenciesMapper {
    Currency toEntity(CurrencyCreateRequest request);

    CurrencyResponse toResponse(Currency currencies);

    List<CurrencyResponse> toResponses(List<Currency> currencies);

    void updateEntity(@MappingTarget Currency currencies, CurrencyUpdateRequest request);
}