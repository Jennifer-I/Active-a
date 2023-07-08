package com.time.tracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Getter
public class myOwnException extends RuntimeException{
    private String message;
    private final LocalDateTime time = LocalDateTime.now();
    private HttpStatus status;

    public myOwnException(String message) {
        this.message = message;
    }
    public myOwnException(String message, HttpStatus status)
    {
        this.message = message;
        this.status = status;
    }
}
