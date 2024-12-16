package com.cshare.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import com.cshare.user.dto.ErrorDto;
import com.cshare.user.exceptions.NotFoundException;
import com.cshare.user.exceptions.PermissionException;
import com.cshare.user.utils.ErrorUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseStatusExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ErrorDto>> handleNotFoundException(NotFoundException ex, ServerWebExchange exchange) {
        HttpStatusCode statusCode = HttpStatus.NOT_FOUND;
        return Mono.just(ResponseEntity.status(statusCode)
                .body(ErrorUtils.createError(statusCode, exchange, ex.getMessage())));
    }

    @ExceptionHandler(PermissionException.class)
    public Mono<ResponseEntity<ErrorDto>> handlePermissionException(PermissionException ex,
            ServerWebExchange exchange) {
        HttpStatusCode statusCode = HttpStatus.FORBIDDEN;
        return Mono.just(ResponseEntity.status(statusCode)
                .body(ErrorUtils.createError(statusCode, exchange, ex.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorDto>> handlePermissionException(IllegalArgumentException ex,
            ServerWebExchange exchange) {
        HttpStatusCode statusCode = HttpStatus.BAD_REQUEST;
        return Mono.just(ResponseEntity.status(statusCode)
                .body(ErrorUtils.createError(statusCode, exchange, ex.getMessage())));
    }

    @ExceptionHandler(ClassCastException.class)
    public Mono<ResponseEntity<ErrorDto>> handlePermissionException(ClassCastException ex,
            ServerWebExchange exchange) {
        HttpStatusCode statusCode = HttpStatus.BAD_REQUEST;
        return Mono.just(ResponseEntity.status(statusCode)
                .body(ErrorUtils.createError(statusCode, exchange,
                        "A file attribute contains a non-file value instead of a file")));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorDto>> handleExchangeBindException(WebExchangeBindException ex,
            ServerWebExchange exchange) {
        HttpStatusCode statusCode = ex.getStatusCode();
        return Flux.fromIterable(ex.getFieldErrors())
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collectList().map(errors -> ResponseEntity.status(statusCode)
                        .body(ErrorUtils.createError(statusCode, exchange, String.join(", ", errors))));
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorDto>> handleBadRequestException(ServerWebInputException ex,
            ServerWebExchange exchange) {
        HttpStatusCode statusCode = ex.getStatusCode();
        return Mono.just(ResponseEntity.status(statusCode)
                .body(ErrorUtils.createError(statusCode, exchange, ex.getReason())));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorDto>> handleGenericException(Exception ex,
            ServerWebExchange exchange) {
        System.out.println(ex);
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        return Mono.just(ResponseEntity.status(statusCode)
                .body(ErrorUtils.createError(statusCode, exchange, "Internal Server Error")));
    }
}
