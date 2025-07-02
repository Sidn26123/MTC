package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionsCreateRequest;
import com.sidn.metruyenchu.paymentservice.entity.Currency;
import com.sidn.metruyenchu.paymentservice.entity.Transactions;
import com.sidn.metruyenchu.paymentservice.entity.Wallet;
import com.sidn.metruyenchu.paymentservice.enums.TransactionType;
import com.sidn.metruyenchu.paymentservice.mapper.TransactionsMapper;
import com.sidn.metruyenchu.paymentservice.repository.TransactionsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TransactionsService {
    TransactionsRepository transactionsRepository;
    TransactionsMapper transactionsMapper;

    public Transactions createTransaction(TransactionsCreateRequest request) {
        // Create a new transaction record
        Transactions transaction = transactionsMapper.toEntity(request);
        transaction.setTransactionCode(generateTransactionCode(request.getType()));

        // Save the transaction to the database (assuming there's a repository for Transactions)
         transactionsRepository.save(transaction);

        return transaction;
    }


    public String generateTransactionCode() {
        // Generate a unique transaction code
        // This could be a UUID, a sequence number, or any other unique identifier
        return "TXN-" + System.currentTimeMillis();
    }

    public String generateTransactionCode(TransactionType transactionType) {
        // Generate a unique transaction code based on the transaction type
        return transactionType.name() + "-" + System.currentTimeMillis() + "." + (int) (Math.random() * 1000);
    }


}
