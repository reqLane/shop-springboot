package com.naukma.shopspringboot.exception;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String msg) {
        super(msg);
    }
}
