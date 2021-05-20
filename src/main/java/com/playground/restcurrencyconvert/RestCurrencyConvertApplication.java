package com.playground.restcurrencyconvert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

import java.util.TimeZone;

@SpringBootApplication
public class RestCurrencyConvertApplication {

    static {
        BlockHound.install(
                builder -> builder.allowBlockingCallsInside("java.util.UUID", "randomUUID")
                        .allowBlockingCallsInside("java.io.FilterInputStream", "read")
                        .allowBlockingCallsInside("java.io.InputStream", "readNBytes")
        );
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(RestCurrencyConvertApplication.class, args);
    }

}
