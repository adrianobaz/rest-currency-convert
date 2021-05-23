package com.playground.restcurrencyconvert.service;

import com.playground.restcurrencyconvert.model.ConversionTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collection;

public interface IConversionTransactionService {

    Flux<ConversionTransaction> applyRateAndSave(Integer userId, String originCurrency,
                                                 BigDecimal originValue, Collection<String> destinyCurrencys);

    Flux<ConversionTransaction> retrieveAllConvertTransactionsByUserId(Integer userId);

    Mono<ConversionTransaction> saveConvertTransaction(ConversionTransaction conversionTransaction);

}
