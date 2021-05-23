package com.playground.restcurrencyconvert.util;

import com.playground.restcurrencyconvert.api.model.output.ConversionTransactionOutput;
import com.playground.restcurrencyconvert.model.ConversionTransaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ConversionTransactionCreator {

    public static ConversionTransaction createConversionTransactionToBeSaved() {
        return ConversionTransaction.builder()
                .userId(1)
                .originCurrency("USD")
                .originValue(BigDecimal.valueOf(109.89))
                .destinyCurrency("BRL")
                .conversionRate(BigDecimal.valueOf(1.2213123))
                .creationDateTime(OffsetDateTime.now())
                .build();
    }

    public static ConversionTransaction createValidConversionTransaction() {
        return ConversionTransaction.builder()
                .id(1L)
                .userId(1)
                .originCurrency("USD")
                .originValue(BigDecimal.valueOf(109.89))
                .destinyCurrency("BRL")
                .conversionRate(BigDecimal.valueOf(1.2213123))
                .creationDateTime(OffsetDateTime.now())
                .build();
    }

    public static ConversionTransactionOutput createConversionTransactionOutput(ConversionTransaction conversionTransaction) {
        return new ConversionTransactionOutput(conversionTransaction);
    }

}
