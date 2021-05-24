package com.playground.restcurrencyconvert.api.controller;

import com.playground.restcurrencyconvert.api.mapper.ConversionTransactionMapper;
import com.playground.restcurrencyconvert.api.model.input.ConversionTransactionInput;
import com.playground.restcurrencyconvert.api.model.output.ConversionTransactionOutput;
import com.playground.restcurrencyconvert.service.IConversionTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/conversion-transactions")
@RequiredArgsConstructor
@Slf4j
public class ConversionTransactionController {

    private final IConversionTransactionService conversionTransactionService;

    private final ConversionTransactionMapper conversionTransactionMapper;

    @Operation(description = "Search exchange rates and save requested transactions",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Save transactions successfully!"),
                    @ApiResponse(responseCode = "400", description = "Request has an error(s)!")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<ConversionTransactionOutput> register(
            @Valid @RequestBody ConversionTransactionInput conversionTransactionInput) {
        Integer userId = conversionTransactionInput.getUserId();
        String originCurrency = conversionTransactionInput.getOriginCurrency().toUpperCase();
        BigDecimal originValue = conversionTransactionInput.getOriginValue();
        Collection<String> destinyCurrencys = conversionTransactionInput.getDestinyCurrencys()
                .stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        return conversionTransactionMapper.toRepresentationModel(
                conversionTransactionService.applyRateAndSave(userId, originCurrency, originValue, destinyCurrencys))
                .log();

    }

}
