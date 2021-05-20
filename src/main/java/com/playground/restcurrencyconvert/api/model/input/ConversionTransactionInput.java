package com.playground.restcurrencyconvert.api.model.input;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Collection;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionTransactionInput {

    @NotNull(message = "User id cannot be empty.")
    private Integer userId;

    @NotBlank(message = "Initial currency origin cannot be empty.")
    @Size(min = 2, max = 5, message = "Initial currency origin cannot be less than 2 and greater than 5")
    private String originCurrency;

    @NotNull(message = "Origin value cannot be empty")
    @Digits(integer = 20, fraction = 2)
    private BigDecimal originValue;

    @NotEmpty(message = "Cannot be empty!")
    private Collection<String> destinyCurrencys;

}
