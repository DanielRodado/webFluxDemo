package com.example.webfluxdemo.exceptions;

public class NotFoundItemException extends RuntimeException {

    public NotFoundItemException(String message) {
        super(message);
    }

}
