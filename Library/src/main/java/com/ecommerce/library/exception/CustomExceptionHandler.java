package com.ecommerce.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleSysException(Exception ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorRes(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleLoginException(BadCredentialsException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorRes(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleCategoryException(CategoryNotFoundException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorRes(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}
