package com.cshare.user.utils;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ServerWebExchange;

import com.cshare.user.dto.ErrorDto;

public class ErrorUtils {
    private ErrorUtils() {
    }

    public static ErrorDto createError(HttpStatusCode statusCode, ServerWebExchange exchange, String message) {
        String error = HttpStatus.valueOf(statusCode.value()).getReasonPhrase();
        if (error.endsWith(" Error")) {
            error = error.substring(0, error.length() - " Error".length());
        }
        return ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .path(exchange.getRequest().getPath().toString())
                .status(statusCode.value())
                .error(error)
                .requestId(exchange.getRequest().getId())
                .message(message)
                .build();
    }
}
