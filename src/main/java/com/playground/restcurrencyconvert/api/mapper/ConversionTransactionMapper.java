package com.playground.restcurrencyconvert.api.mapper;

import com.playground.restcurrencyconvert.api.model.output.ConversionTransactionOutput;
import com.playground.restcurrencyconvert.model.ConversionTransaction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ConversionTransactionMapper {

    public Flux<ConversionTransactionOutput> toRepresentationModel(Flux<ConversionTransaction> conversionTransactionFlux) {
        return conversionTransactionFlux.map(this::toRepresentationModel);
    }

    public ConversionTransactionOutput toRepresentationModel(ConversionTransaction conversionTransaction) {
        return new ConversionTransactionOutput(conversionTransaction);
    }

}
