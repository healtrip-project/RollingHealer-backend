package com.ssafy.rollinghealer.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleNoSuchElementFoundException(Exception e) {
        log.error("에러 발생 {}, : {}",e.getLocalizedMessage(),e.getStackTrace());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("에러");
    }
}