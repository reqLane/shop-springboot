package com.naukma.shopspringboot.exception;

public class InvalidOrderDataException extends RuntimeException {
    public InvalidOrderDataException(String msg) {
        super(msg);
    }
}
