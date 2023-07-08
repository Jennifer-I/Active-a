package com.time.tracker.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class PasswordAndEmailNotFoundException extends RuntimeException {
    private String message;
    private final LocalDateTime time = LocalDateTime.now();
    private HttpStatus status;

    public PasswordAndEmailNotFoundException(String message) {
        this.message = message;
    }

    public PasswordAndEmailNotFoundException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
