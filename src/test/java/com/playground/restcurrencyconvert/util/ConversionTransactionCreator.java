package com.playground.restcurrencyconvert.util;

import com.playground.restcurrencyconvert.model.ConversionTransaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ConversionTransactionCreator {

    public static ConversionTransaction createConversionTransactionToBeSaved() {
        return ConversionTransaction.builder()
                .userId(1)
                .originCurrency("USD")
                .originValue(BigDecimal.TEN)
                .destinyCurrency("BRL")
                .conversionRate(BigDecimal.valueOf(1.2213123))
                .creationDateTime(OffsetDateTime.now())
                .build();
    }


}
