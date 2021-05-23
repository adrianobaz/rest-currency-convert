package com.playground.restcurrencyconvert.service.impl;

import com.playground.restcurrencyconvert.model.ConversionTransaction;
import com.playground.restcurrencyconvert.repository.IConversionTransactionRepository;
import com.playground.restcurrencyconvert.util.ConversionTransactionCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ConversionTransactionServiceImplTest {

    private final ConversionTransaction conversionTransactionValid = ConversionTransactionCreator.createValidConversionTransaction();

    private final ConversionTransaction conversionTransactionToBeSaved = ConversionTransactionCreator.createConversionTransactionToBeSaved();

    @InjectMocks
    private ConversionTransactionServiceImpl conversionTransactionService;
    @Mock
    private IConversionTransactionRepository conversionTransactionRepository;

    @Autowired
    private WebTestClient webClient;

    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        BDDMockito.given(conversionTransactionRepository.findAllByUserId(ArgumentMatchers.anyInt()))
                .willReturn(Flux.just(conversionTransactionValid));

        BDDMockito.given(conversionTransactionRepository.save(conversionTransactionToBeSaved))
                .willReturn(Mono.just(conversionTransactionValid));
    }

    @Test
    void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });
            Schedulers.parallel().schedule(task);

            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    @Test
    @DisplayName("findAllByUserId returns a Flux with conversion transactions when it exists")
    void findAllByUserId_ReturnFluxConversionTransactions_WhenSuccessful() {
        StepVerifier.create(conversionTransactionService.retrieveAllConvertTransactionsByUserId(1))
                .expectSubscription()
                .expectNext(conversionTransactionValid)
                .verifyComplete();
    }

    @Test
    @DisplayName("findAllByUserId returns a Flux with error when it conversion transactions does not exists")
    void findAllByUserId_ReturnFluxError_WhenErroEmptyFluxIsReturned() {
        BDDMockito.when(conversionTransactionRepository.findAllByUserId(ArgumentMatchers.anyInt()))
                .thenReturn(Flux.empty());

        StepVerifier.create(conversionTransactionService.retrieveAllConvertTransactionsByUserId(ArgumentMatchers.anyInt()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("save create an conversion transactions when successful")
    void save_CreateConversionTransaction_WhenSuccessful() {

        StepVerifier.create(conversionTransactionService.saveConvertTransaction(conversionTransactionToBeSaved))
                .expectSubscription()
                .expectNext(conversionTransactionValid)
                .verifyComplete();
    }

}