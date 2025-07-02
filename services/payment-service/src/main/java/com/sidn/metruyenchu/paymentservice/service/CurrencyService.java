package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.currency.CurrencyCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.currency.CurrencyUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.currency.CurrencyResponse;
import com.sidn.metruyenchu.paymentservice.entity.Currency;
import com.sidn.metruyenchu.paymentservice.enums.CurrencyStatus;
import com.sidn.metruyenchu.paymentservice.exception.AppException;
import com.sidn.metruyenchu.paymentservice.exception.ErrorCode;
import com.sidn.metruyenchu.paymentservice.mapper.CurrencyMapper;
import com.sidn.metruyenchu.paymentservice.repository.CurrencyRepository;
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
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CurrencyService {
    CurrencyRepository currencyRepository;
    CurrencyMapper currencyMapper;

    public void addCurrency() {
        Currency currency = Currency.builder()
                .code("ABC")
                .currencyStatus(CurrencyStatus.ACTIVE)
                .build();
        currencyRepository.save(currency);
    }

    public CurrencyResponse getCurrencyById(String id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));

        return currencyMapper.toResponse(currency);
    }

    public Currency getCurrencyEntityById(String id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));
    }

    public Currency getCurrencyEntityByCode(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));
    }

    public Currency getDefaultPurchaseCurrency() {
        return currencyRepository.findByCode("XU")
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));
    }

    public CurrencyResponse createCurrency(CurrencyCreateRequest request) {
        Currency currency = currencyMapper.toEntity(request);
        currency.setCurrencyStatus(CurrencyStatus.ACTIVE);
        try{
            currency = currencyRepository.save(currency);
        } catch (AppException e) {
            log.info("Co loi xay ra: {}", e.getMessage());
            throw new AppException(ErrorCode.CURRENCY_ALREADY_EXISTS);
        }
        return currencyMapper.toResponse(currency);
    }

    public PageResponse<CurrencyResponse> getAllCurrencies(BaseFilterRequest request){
        Pageable pageable = PageUtils.from(request);

        Page<Currency> pageData = currencyRepository.findAllByCurrencyStatus(
                CurrencyStatus.ACTIVE,
                pageable
        );

        PageResponse<CurrencyResponse> pageResponse = PageUtils.toPageResponse(
                pageData,
                currencyMapper::toResponse,
                request.getPage()
        );

        return pageResponse;
    }

    public CurrencyResponse updateCurrency(String id, CurrencyUpdateRequest request) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));

        currencyMapper.updateEntity(currency, request);

        return currencyMapper.toResponse(currencyRepository.save(currency));
    }

    public void hardDeleteCurrency(String id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));

        currencyRepository.delete(currency);

    }
}
