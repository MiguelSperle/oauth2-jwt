package com.example.oauth2.configurations;

import com.example.oauth2.exceptions.CsrfTokenRequiredException;
import com.example.oauth2.exceptions.InvalidCsrfTokenException;
import com.example.oauth2.utils.ApiError;
import com.example.oauth2.utils.InstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(final Exception ex) {
        log.error("Handling unexpected exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiError.from(
                Collections.singletonList("An unexpected error occurred"), InstantUtils.now()
        ));
    }

    @ExceptionHandler(CsrfTokenRequiredException.class)
    public ResponseEntity<ApiError> handleCsrfTokenRequiredException(final CsrfTokenRequiredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.from(
                Collections.singletonList(ex.getMessage()), InstantUtils.now()
        ));
    }

    @ExceptionHandler(InvalidCsrfTokenException.class)
    public ResponseEntity<ApiError> handleInvalidCsrfTokenException(final InvalidCsrfTokenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiError.from(
                Collections.singletonList(ex.getMessage()), InstantUtils.now()
        ));
    }
}
