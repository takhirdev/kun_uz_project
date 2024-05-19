package com.example.controller;

import com.example.exception.AppBadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerController {
    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<String> exp (AppBadException exception){
        return ResponseEntity.ok(exception.getMessage());
    }
}
