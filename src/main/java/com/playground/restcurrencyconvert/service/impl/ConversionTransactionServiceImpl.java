package com.playground.restcurrencyconvert.service.impl;

import com.playground.restcurrencyconvert.model.ConversionTransaction;
import com.playground.restcurrencyconvert.repository.IConversionTransactionRepository;
import com.playground.restcurrencyconvert.service.IConversionTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversionTransactionServiceImpl implements IConversionTransactionService {

    private final IConversionTransactionRepository conversionTransactionRepository;

    private final WebClient webClientExchangeRatesApi;

    private final WebClient webClientCurrConvApi;

    @Override
    public void applyRateAndSave(Integer userId, String originCurrency, BigDecimal originValue, Collection<String> destinyCurrencys) {

    }

    @Override
    public Flux<ConversionTransaction> retrieveAllTransactionsByUserId(Integer userId) {
        return conversionTransactionRepository.findAllByUserId(userId);
    }

    private void registerTransaction(Integer userId, BigDecimal originValue, String originCurrencyUpper,
                                     String destinyCurrency, BigDecimal conversionRate) {
        ConversionTransaction conversionTransaction = new ConversionTransaction();
        conversionTransaction.setUserId(userId);
        conversionTransaction.setOriginCurrency(originCurrencyUpper);
        conversionTransaction.setOriginValue(originValue);
        conversionTransaction.setDestinyCurrency(destinyCurrency);
        conversionTransaction.setConversionRate(conversionRate);
        conversionTransactionRepository.save(conversionTransaction);
    }
}
