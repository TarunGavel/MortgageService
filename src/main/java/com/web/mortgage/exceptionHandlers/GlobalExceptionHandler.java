package com.web.mortgage.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnsupportedMaturityPeriodException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedPeriod(UnsupportedMaturityPeriodException ex) {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST)
               .body(Map.of("status", 400 , "error ", "Bad Request", "message", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status", 400 , "error ", "Bad Request",
                        "message", "Validation failed", "errors" , fieldErrors));
    }
}
