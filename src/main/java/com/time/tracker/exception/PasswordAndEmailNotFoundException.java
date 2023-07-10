package com.time.tracker.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
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
