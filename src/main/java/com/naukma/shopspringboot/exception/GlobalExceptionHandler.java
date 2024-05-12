package com.naukma.shopspringboot.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, String>> handleExceptions(Exception e) {
        Throwable rootCause = getRootCause(e);
        if (rootCause instanceof ConstraintViolationException)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap(getConstraintViolationMessages((ConstraintViolationException) rootCause)));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap(List.of(e.getMessage())));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Map<String, String>> handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap(List.of(e.getMessage())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Map<String, String>> handleEntityNotFoundExceptions(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap(List.of(e.getMessage())));
    }

    @ExceptionHandler(InvalidOrderDataException.class)
    public final ResponseEntity<Map<String, String>> handleInvalidOrderDataExceptions(InvalidOrderDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap(List.of(e.getMessage())));
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public final ResponseEntity<Map<String, String>> handleInvalidUserDataExceptions(InvalidUserDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap(List.of(e.getMessage())));
    }

    private Throwable getRootCause(Throwable e) {
        Throwable rootCause = e;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    private Map<String, String> errorMap(List<String> messages) {
        Map<String, String> map = new LinkedHashMap<>();
        for(int i = 0; i < messages.size(); i++) {
            map.put("Error#"+(i+1), messages.get(i));
        }
        return map;
    }

    private List<String> getConstraintViolationMessages(ConstraintViolationException e) {
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            messages.add(violation.getMessage());
        }
        return messages;
    }
}
