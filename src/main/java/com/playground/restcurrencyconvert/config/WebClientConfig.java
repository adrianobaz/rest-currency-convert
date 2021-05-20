package com.playground.restcurrencyconvert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final String BASE_URL_EXCHANGE_RATE_API
            = "http://api.exchangeratesapi.io/v1/";

    private static final String BASE_URL_CURRCONV_API
            = "http://free.currconv.com/api/v7/convert?apiKey=f6c3dba59f19e0bbe471";

    @Bean
    public WebClient webClientExchangeRatesApi(WebClient.Builder builder) {
        return builder
                .baseUrl(BASE_URL_EXCHANGE_RATE_API)
                .build();
    }

    @Bean
    public WebClient webClientCurrConvApi(WebClient.Builder builder) {
        return builder
                .baseUrl(BASE_URL_CURRCONV_API)
                .build();
    }

}
