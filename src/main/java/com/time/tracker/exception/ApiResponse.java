package com.time.tracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Getter
public class ApiResponse extends RuntimeException{
    private String message;
    private final LocalDateTime time = LocalDateTime.now();
    private HttpStatus status;

    public ApiResponse(String message) {
        this.message = message;
    }
    public ApiResponse(String message, HttpStatus status)
    {
        this.message = message;
        this.status = status;
    }
}
