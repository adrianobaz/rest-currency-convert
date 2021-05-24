package com.playground.restcurrencyconvert.api.controller;


import com.playground.restcurrencyconvert.api.mapper.ConversionTransactionMapper;
import com.playground.restcurrencyconvert.api.model.output.ConversionTransactionOutput;
import com.playground.restcurrencyconvert.service.IConversionTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IConversionTransactionService conversionTransactionService;

    private final ConversionTransactionMapper conversionTransactionMapper;

    @Operation(description = "Conversion transactions are sent to the client as Server Sent Events",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stream all conversion transactions successfully!"),
                    @ApiResponse(responseCode = "404", description = "User not exist!")
            })
    @GetMapping(value = "/{id}/conversion-transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<ConversionTransactionOutput> requestConversionTransactionsByUserId(@PathVariable(value = "id") Integer userId) {
        return conversionTransactionMapper.toRepresentationModel(
                conversionTransactionService.retrieveAllConvertTransactionsByUserId(userId)
        ).log();
    }

}
