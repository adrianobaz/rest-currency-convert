package com.playground.restcurrencyconvert.repository;

import com.playground.restcurrencyconvert.model.ConversionTransaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IConversionTransactionRepository extends ReactiveCrudRepository<ConversionTransaction, Long> {

    @Query("SELECT * FROM currency_exchanges.conversion_transaction WHERE user_id = :userId")
    Flux<ConversionTransaction> findAllByUserId(Integer userId);

}
