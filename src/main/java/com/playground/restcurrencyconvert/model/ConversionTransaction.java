package com.playground.restcurrencyconvert.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Table("conversion_transaction")
public class ConversionTransaction {

    @Getter
    @Id
    private Long id;

    @Getter
    @Setter
    @NotNull
    private Integer userId;

    @Getter
    @Setter
    @NotBlank
    private String originCurrency;

    @Getter
    @Setter
    @NotNull
    private BigDecimal originValue;

    @Getter
    @Setter
    @NotBlank
    private String destinyCurrency;

    @Getter
    @Setter
    @NotNull
    private BigDecimal conversionRate;

    @Getter
    @Setter
    private OffsetDateTime creationDateTime;


    public BigDecimal calculateDestinyValue(BigDecimal originValue, BigDecimal conversionRate) {
        return originValue.multiply(conversionRate);
    }

}