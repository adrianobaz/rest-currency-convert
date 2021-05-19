package com.playground.restcurrencyconvert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.TimeZone;

@SpringBootApplication
public class RestCurrencyConvertApplication {

	@Bean
	public WebClient webClientExchangeRatesApi(WebClient.Builder builder) {
		return builder
				.baseUrl("http://api.exchangeratesapi.io")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	@Bean
	public WebClient webClientCurrConvApi(WebClient.Builder builder) {
		return builder
				.baseUrl("http://free.currconv.com/api/v7/convert?apiKey=f6c3dba59f19e0bbe471")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(RestCurrencyConvertApplication.class, args);
	}

}
