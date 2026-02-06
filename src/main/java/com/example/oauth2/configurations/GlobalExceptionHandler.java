package com.example.oauth2.configurations;

import com.example.oauth2.exceptions.CsrfTokenRequiredException;
import com.example.oauth2.exceptions.InvalidCsrfTokenException;
import com.example.oauth2.utils.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        log.error("Handling unexpected exception: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(ApiError.from(
                Collections.singletonList("An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        ));
    }

    @ExceptionHandler(CsrfTokenRequiredException.class)
    public ResponseEntity<ApiError> handleCsrfTokenRequiredException(CsrfTokenRequiredException ex) {
        log.info("Csrf token required exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ApiError.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()
        ));
    }

    @ExceptionHandler(InvalidCsrfTokenException.class)
    public ResponseEntity<ApiError> handleInvalidCsrfTokenException(InvalidCsrfTokenException ex) {
        log.info("Invalid Csrf token exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ApiError.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()
        ));
    }
}
