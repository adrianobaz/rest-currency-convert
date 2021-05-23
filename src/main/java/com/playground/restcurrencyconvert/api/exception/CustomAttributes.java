package com.playground.restcurrencyconvert.api.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomAttributes extends DefaultErrorAttributes {

    private static final String REGEX_FIELD = "\\|[a-zA-Z]+(?: +[a-zA-Z]+)*";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributesMap = super.getErrorAttributes(request, options);
        Throwable throwable = getError(request);
        if(throwable instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) throwable;
            String exMessageDescription = ex.getMessage();
            errorAttributesMap.remove("requestId");
            errorAttributesMap.put("message", ex.getMessage());
            errorAttributesMap.put("description", fieldWithDescription(exMessageDescription));
            return errorAttributesMap;
        }
        return errorAttributesMap;
    }

    public static String fieldWithDescription(String field){
        Pattern defaultPattern = Pattern.compile(REGEX_FIELD);

        Matcher matcherField = defaultPattern.matcher(field);

        if(matcherField.find()){
            field = matcherField.group(0);
        }

        return field;
    }

}
