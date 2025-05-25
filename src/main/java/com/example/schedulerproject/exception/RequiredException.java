package com.example.schedulerproject.exception;

import org.springframework.http.HttpStatus;

public class RequiredException extends ApiException {
    public RequiredException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
