package com.ssafy.rollinghealer.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleNoSuchElementFoundException(Exception e) {
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("에러");
    }
}