package com.time.tracker.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerObj {
    @ExceptionHandler(myOwnException.class)
    public ResponseEntity<?>handleMyOwnException(myOwnException es)
    {
        return ResponseEntity.badRequest().body(es.getLocalizedMessage());
    }

    public ResponseEntity<?>PasswordAndEmailNotFoundException(myOwnException e)
    {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }


}