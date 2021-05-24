package com.playground.restcurrencyconvert.api.controller;

import com.playground.restcurrencyconvert.api.mapper.ConversionTransactionMapper;
import com.playground.restcurrencyconvert.api.model.output.ConversionTransactionOutput;
import com.playground.restcurrencyconvert.model.ConversionTransaction;
import com.playground.restcurrencyconvert.service.IConversionTransactionService;
import com.playground.restcurrencyconvert.util.ConversionTransactionCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private IConversionTransactionService conversionTransactionService;
    @Mock
    private ConversionTransactionMapper conversionTransactionMapper;

    private ConversionTransaction conversionTransaction = ConversionTransactionCreator.createValidConversionTransaction();

    private ConversionTransactionOutput conversionTransactionOutput = ConversionTransactionCreator.createConversionTransactionOutput(conversionTransaction);

    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {

//        BDDMockito.given(conversionTransactionService.retrieveAllConvertTransactionsByUserId(ArgumentMatchers.anyInt()))
//                .willReturn(Flux.just(createValidConversionTransaction()));

    }

    @Test
    void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0); //NOSONAR
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
    @DisplayName("test one")
    void register() {
//        StepVerifier.create(conversionTransactionController.requestConversionTransactionsByUserId(1))
//                .expectSubscription()
//                .expectNext(conversionTransactionOutput)
//                .verifyComplete();
    }

}