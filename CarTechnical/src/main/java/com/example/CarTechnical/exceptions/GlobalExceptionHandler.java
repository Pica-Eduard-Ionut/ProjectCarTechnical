package com.example.CarTechnical.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex) {
        return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    }
}