package com.playground.restcurrencyconvert.api.controller;

import com.playground.restcurrencyconvert.api.mapper.ConversionTransactionMapper;
import com.playground.restcurrencyconvert.api.model.input.ConversionTransactionInput;
import com.playground.restcurrencyconvert.api.model.output.ConversionTransactionOutput;
import com.playground.restcurrencyconvert.service.IConversionTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/conversion-transactions")
@RequiredArgsConstructor
@Slf4j
public class ConversionTransactionController {

    private final IConversionTransactionService conversionTransactionService;

    private final ConversionTransactionMapper conversionTransactionMapper;

    @Operation(description = "Search conversion rates and save requested transactions",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Save transactions successfully!"),
                    @ApiResponse(responseCode = "400", description = "Request has an error!")
            })
    @PostMapping
    public Flux<ConversionTransactionOutput> register(@RequestBody @Valid ConversionTransactionInput conversionTransactionInput) {
        Integer userId = conversionTransactionInput.getUserId();
        String originCurrency = conversionTransactionInput.getOriginCurrency().toUpperCase();
        BigDecimal originValue = conversionTransactionInput.getOriginValue().setScale(2, RoundingMode.HALF_EVEN);
        Collection<String> destinyCurrencys = conversionTransactionInput.getDestinyCurrencys()
                .stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        return conversionTransactionMapper.toRepresentationModel(
                conversionTransactionService.applyRateAndSave(userId, originCurrency, originValue, destinyCurrencys));
    }

    @Operation(description = "Conversion transactions are sent to the client as Server Sent Events",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stream all conversion transactions successfully!")
            })
    @GetMapping(value = "/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ConversionTransactionOutput> requestConversionTransactionsByUserId(@PathVariable Integer userId) {
        return conversionTransactionMapper.toRepresentationModel(conversionTransactionService
                .retrieveAllConvertTransactionsByUserId(userId))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(conversionTransactionOutput -> log.info("{}", conversionTransactionOutput));
    }

}
