package com.example.webfluxdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundItemException.class)
    public Mono<ResponseEntity<String>> handleNotFoundItemException(NotFoundItemException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
    }

    @ExceptionHandler(InvalidItemNameException.class)
    public Mono<ResponseEntity<String>> handleInvalidItemNameException(InvalidItemNameException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage()));
    }
}
