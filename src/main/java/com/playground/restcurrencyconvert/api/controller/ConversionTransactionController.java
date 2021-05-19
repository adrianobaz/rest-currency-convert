package com.playground.restcurrencyconvert.api.controller;

import com.playground.restcurrencyconvert.api.model.input.ConversionTransactionInput;
import com.playground.restcurrencyconvert.api.model.output.ConversionTransactionOutput;
import com.playground.restcurrencyconvert.model.ConversionTransaction;
import com.playground.restcurrencyconvert.service.IConversionTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping("/v1/conversion-transactions")
@RequiredArgsConstructor
public class ConversionTransactionController {

    private final IConversionTransactionService conversionTransactionService;

    @PostMapping
    public Flux<ConversionTransactionOutput> register(@RequestBody ConversionTransactionInput conversionTransactionInput) {
        Integer userId = conversionTransactionInput.getUserId();
        String originCurrency = conversionTransactionInput.getOriginCurrency();
        BigDecimal originValue = conversionTransactionInput.getOriginValue();
        Collection<String> destinyCurrencys = conversionTransactionInput.getDestinyCurrencys();
        conversionTransactionService.applyRateAndSave(userId, originCurrency, originValue, destinyCurrencys);
        return null;
    }

    @GetMapping("/{userId}")
    public Flux<ConversionTransactionOutput> requestConversionTransactionsByUserId(@PathVariable Integer userId) {
        Flux<ConversionTransaction> conversionTransactionFlux =
                conversionTransactionService.retrieveAllTransactionsByUserId(userId);
        return conversionTransactionFlux.flatMap(conversionTransaction ->
                Mono.just(this.toRepresentationModel(conversionTransaction)));
    }

    private ConversionTransactionOutput toRepresentationModel(ConversionTransaction conversionTransaction) {
        return new ConversionTransactionOutput(conversionTransaction);
    }

}
