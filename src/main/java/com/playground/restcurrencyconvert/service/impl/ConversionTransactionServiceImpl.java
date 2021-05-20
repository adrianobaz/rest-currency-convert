package com.playground.restcurrencyconvert.service.impl;

import com.playground.restcurrencyconvert.model.ConversionTransaction;
import com.playground.restcurrencyconvert.model.ExchangeRateValue;
import com.playground.restcurrencyconvert.repository.IConversionTransactionRepository;
import com.playground.restcurrencyconvert.service.IConversionTransactionService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversionTransactionServiceImpl implements IConversionTransactionService {

    private final IConversionTransactionRepository conversionTransactionRepository;

    private final WebClient webClientExchangeRatesApi;

    private final WebClient webClientCurrConvApi;

    @Override
    public void applyRateAndSave(Integer userId, String originCurrency, BigDecimal originValue, Collection<String> destinyCurrencys) {
        String originCurrencyUpper = originCurrency.toUpperCase();
        String destinyCurrencysCommaSeparated = destinyCurrencys.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(Character.toString(StringUtil.COMMA)));

        log.info("API request to retrieve exchange rates ...");

        // IMPLEMENT HANDLING ERRORS
        Mono<ExchangeRateValue> exchangeRateValueMono = this.webClientExchangeRatesApi
                .get()
                .uri(getUriExchangeRateApiBaseSymbolsCurrency(originCurrencyUpper, destinyCurrencysCommaSeparated))
                .retrieve()
                .bodyToMono(ExchangeRateValue.class);

        log.info("Processing API request to retrieve exchange rates...");

        exchangeRateValueMono.subscribe(exchangeRateValue -> {

            log.info("Requested information was returned successfully!...");

            log.info("Exchange Rate values: {}", exchangeRateValue);

//            exchangeRateValue.getRates().forEach((destinyCurrency, conversionRate) -> {
//                saveTransaction(userId, originValue, originCurrencyUpper, destinyCurrency, conversionRate);
//                log.info("Records persisted successfully!");
//            });
        });

    }

    @Override
    public Flux<ConversionTransaction> retrieveAllTransactionsByUserId(Integer userId) {
        return conversionTransactionRepository.findAllByUserId(userId);
    }


    public void saveTransaction(Integer userId, BigDecimal originValue, String originCurrencyUpper,
                                     String destinyCurrency, BigDecimal conversionRate) {
        ConversionTransaction conversionTransaction = new ConversionTransaction();
        conversionTransaction.setUserId(userId);
        conversionTransaction.setOriginCurrency(originCurrencyUpper);
        conversionTransaction.setOriginValue(originValue);
        conversionTransaction.setDestinyCurrency(destinyCurrency);
        conversionTransaction.setConversionRate(conversionRate);
        conversionTransactionRepository.save(conversionTransaction);
    }

    private Function<UriBuilder, URI> getUriExchangeRateApiBaseSymbolsCurrency(String originCurrencyUpper, String destinyCurrencysCommaSeparated) {
        return uriBuilder ->
                uriBuilder.path(StringUtil.EMPTY_STRING)
                        .queryParam("base", originCurrencyUpper)
                        .queryParam("symbols", destinyCurrencysCommaSeparated)
                        .build();
    }
}
