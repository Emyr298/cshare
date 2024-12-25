package com.cshare.user.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import com.cshare.user.dto.ErrorDto;
import com.cshare.user.exceptions.NotFoundException;
import com.cshare.user.exceptions.PermissionException;
import com.cshare.user.utils.ErrorUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Component
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseStatusExceptionHandler {
    private final Logger logger;

    @ExceptionHandler({ NotFoundException.class, NoResourceFoundException.class })
    public Mono<ResponseEntity<ErrorDto>> handleNotFound(Exception ex, ServerWebExchange exchange) {
        return createExceptionHelper(HttpStatus.NOT_FOUND, ex.getMessage(), exchange);
    }

    @ExceptionHandler({ PermissionException.class })
    public Mono<ResponseEntity<ErrorDto>> handleForbidden(Exception ex, ServerWebExchange exchange) {
        return createExceptionHelper(HttpStatus.FORBIDDEN, ex.getMessage(), exchange);
    }

    @ExceptionHandler({ MethodNotAllowedException.class })
    public Mono<ResponseEntity<ErrorDto>> handleMethodNotAllowed(Exception ex, ServerWebExchange exchange) {
        return createExceptionHelper(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), exchange);
    }

    @ExceptionHandler({ IllegalArgumentException.class, ClassCastException.class })
    public Mono<ResponseEntity<ErrorDto>> handleBadRequest(Exception ex,
            ServerWebExchange exchange) {
        String message = ex.getMessage();
        if (ex instanceof ClassCastException) {
            message = "A file attribute contains a non-file value instead of a file";
        }
        return createExceptionHelper(HttpStatus.BAD_REQUEST, message, exchange);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorDto>> handleExchangeBindException(WebExchangeBindException ex,
            ServerWebExchange exchange) {
        return Flux.fromIterable(ex.getFieldErrors())
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collectList()
                .map(errors -> String.join(", ", errors))
                .flatMap(msg -> createExceptionHelper(ex.getStatusCode(), msg, exchange));
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorDto>> handleBadRequestException(ServerWebInputException ex,
            ServerWebExchange exchange) {
        return createExceptionHelper(ex.getStatusCode(), ex.getReason(), exchange);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorDto>> handleGenericException(Exception ex,
            ServerWebExchange exchange) {
        logger.error(ex.getMessage(), ex);
        return createExceptionHelper(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", exchange);
    }

    private Mono<ResponseEntity<ErrorDto>> createExceptionHelper(HttpStatusCode statusCode, String message,
            ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(statusCode)
                .body(ErrorUtils.createError(statusCode, exchange, message)));
    }
}
