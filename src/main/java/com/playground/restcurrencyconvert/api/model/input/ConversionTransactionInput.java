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

    @NotNull(message = "ERROR: |User id cannot be empty!|")
    private Integer userId;

    @NotBlank(message = "ERROR: |Initial currency origin cannot be empty!|")
    @Size(min = 3, max = 3, message = " ERROR: |Initial currency origin cannot be less than 3 and greater than 3!|")
    @Pattern(regexp = "([A-Z]+)", message = "ERROR: |Letters cannot be lower case!|")
    private String originCurrency;

    @NotNull(message = "ERROR: (Origin value cannot be empty!|")
    @Digits(integer = 20, fraction = 2, message = "ERROR: |Value cannot have more than 2 decimal places!|")
    private BigDecimal originValue;

    @NotEmpty(message = "Cannot be empty!")
    private Collection<
            @NotBlank(message = "ERROR: |Initial currency origin cannot be empty!|")
            @Size(min = 3, max = 3, message = " ERROR: |Initial currency origin cannot be less than 3 and greater than 3!|")
            @Pattern(regexp = "([A-Z]+)", message = "ERROR: |Letters cannot be lower case!|")
            String> destinyCurrencys;

}
