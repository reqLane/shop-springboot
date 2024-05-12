package com.naukma.shopspringboot.exception;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String msg) {
        super(msg);
    }
}
