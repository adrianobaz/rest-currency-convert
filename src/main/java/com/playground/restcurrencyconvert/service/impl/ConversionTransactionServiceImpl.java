package com.playground.restcurrencyconvert.service.impl;

import com.playground.restcurrencyconvert.model.ConversionTransaction;
import com.playground.restcurrencyconvert.model.ExchangeRateValue;
import com.playground.restcurrencyconvert.repository.IConversionTransactionRepository;
import com.playground.restcurrencyconvert.service.IConversionTransactionService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversionTransactionServiceImpl implements IConversionTransactionService {

    private static final String ACESS_KEY_EXCHANGE_RATE_API = "b925c23ac599142ce5fdc632d57cba8c";
    private final IConversionTransactionRepository conversionTransactionRepository;
    private final WebClient webClientExchangeRatesApi;
    private final WebClient webClientCurrConvApi;

    @Override
    public Flux<ConversionTransaction> applyRateAndSave(Integer userId, String originCurrency, BigDecimal originValue,
                                                        Collection<String> destinyCurrencys) {
        log.info("START of request to API for retrieve exchange rates...");

        // IMPLEMENT HANDLING ERRORS
        Mono<ExchangeRateValue> exchangeRateValueMono = this.webClientExchangeRatesApi
                .get()
                .uri(getUriExchangeRateApiBaseSymbolsCurrency(originCurrency, destinyCurrencys))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .bodyToMono(ExchangeRateValue.class);

        return Flux.concat(
                exchangeRateValueMono.map(exchangeRateValue -> {

                    log.info("Requested information was RETURNED successfully!...");

                    log.info("Exchange Rate values: {}", exchangeRateValue);

                    Collection<Mono<ConversionTransaction>> conversionTransactions = new ArrayList<>();
                    for (Map.Entry<String, BigDecimal> pair : exchangeRateValue.getRates().entrySet()) {
                        Mono<ConversionTransaction> conversionTransactionMono = this.saveConvertTransaction(
                                new ConversionTransaction(userId, originCurrency, originValue,
                                        pair.getKey(), pair.getValue(), OffsetDateTime.now())
                        ).log();
                        conversionTransactions.add(conversionTransactionMono);
                    }
                    log.info("Record(s) persisted successfully!!!");
                    var monoList = Flux.fromIterable(conversionTransactions).flatMap(s -> s).collectList();
                    return monoList.flatMapMany(Flux::fromIterable);
                }));
    }

    @Override
    public Flux<ConversionTransaction> retrieveAllConvertTransactionsByUserId(Integer userId) {
        return conversionTransactionRepository.findAllByUserId(userId);
    }

    @Override
    public Mono<ConversionTransaction> saveConvertTransaction(ConversionTransaction conversionTransaction) {
        return conversionTransactionRepository.save(conversionTransaction);
    }

    private Function<UriBuilder, URI> getUriExchangeRateApiBaseSymbolsCurrency(String originCurrencyUpper,
                                                                               Collection<String> destinyCurrencys) {
        String destinyCurrencysCommaSeparated = destinyCurrencys.stream()
                .collect(Collectors.joining(Character.toString(StringUtil.COMMA)));
        return uriBuilder ->
                uriBuilder.path("latest")
                        .queryParam("access_key", ACESS_KEY_EXCHANGE_RATE_API)
                        .queryParam("base", originCurrencyUpper)
                        .queryParam("symbols", destinyCurrencysCommaSeparated)
                        .build();
    }
}
