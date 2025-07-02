package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.ContentPurchaseCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.contentPurchase.ContentPurchaseResponse;
import com.sidn.metruyenchu.paymentservice.entity.ContentPurchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
@Mapper(componentModel = "spring",
         nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = {TransactionsMapper.class})
public interface ContentPurchaseMapper {
    @Mapping(source = "transaction.id", target = "transactionId")
    ContentPurchaseResponse toResponse(ContentPurchase contentPurchase);

    ContentPurchase toEntity(ContentPurchaseCreateRequest request);

    List<ContentPurchaseResponse> toResponses(List<ContentPurchase> contentPurchases);
}