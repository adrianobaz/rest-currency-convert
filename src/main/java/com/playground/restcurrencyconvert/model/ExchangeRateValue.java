package com.playground.restcurrencyconvert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateValue {

    private boolean sucess;
    private Long timestamp;
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ExchangeRateValue {");
        stringBuilder.append("sucess: ".concat(String.valueOf(sucess)));

        Instant instant = Instant.ofEpochMilli(timestamp);
        OffsetDateTime dt = OffsetDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());

        stringBuilder.append(", timestamp: ".concat(dt.toString()));

        stringBuilder.append(", base: '".concat(base)).append('\'');
        stringBuilder.append(", date: ".concat(date.toString()));
        stringBuilder.append(", rates: ").append(rates).append('}');

        return stringBuilder.toString();
    }
}
