package com.example.schedulerproject.exception;

import com.example.schedulerproject.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordException(PasswordException e, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("about:blank",
                "Password Mismatch",
                e.getHttpStatus().value(),
                e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(errorResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(RequiredException.class)
    public ResponseEntity<ErrorResponseDto> handleRequiredException(RequiredException e, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "about:blank",
                        "Required Field is missing",
                e.getHttpStatus().value(),
                e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(errorResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(ScheduleException.class)
    public ResponseEntity<ErrorResponseDto> handleRequiredException(ScheduleException e, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "about:blank",
                      "Schedule Not Found",
                    e.getHttpStatus().value(),
                e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(errorResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(ApiException.class) // fallback
    public ResponseEntity<ErrorResponseDto> handleApiException(ApiException e, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "about:blank",
                "API error",
                e.getHttpStatus().value(),
                e.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(errorResponseDto, e.getHttpStatus());
    }
}
