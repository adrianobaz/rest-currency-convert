package com.playground.restcurrencyconvert.api.model.output;

import com.playground.restcurrencyconvert.model.ConversionTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversionTransactionOutput {

    private Long id;
    private Integer userId;
    private String originCurrency;
    private BigDecimal originValue;
    private String destinyCurrency;
    private BigDecimal conversionRate;
    private BigDecimal destinyValue;
    private OffsetDateTime creationDateTime;

    public ConversionTransactionOutput(ConversionTransaction conversionTransaction) {
        this.id = conversionTransaction.getId();
        this.userId = conversionTransaction.getUserId();
        this.originCurrency = conversionTransaction.getOriginCurrency();
        this.originValue = conversionTransaction.getOriginValue();
        this.destinyCurrency = conversionTransaction.getDestinyCurrency();
        this.conversionRate = conversionTransaction.getConversionRate();
        this.destinyValue = conversionTransaction.calculateDestinyValue(originValue, conversionRate);
        this.creationDateTime = conversionTransaction.getCreationDateTime();
    }

}
