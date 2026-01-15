package com.example.oauth2.configurations.security.authentication.handlers;

import com.example.oauth2.utils.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String message = "Access denied";

        if (accessDeniedException instanceof MissingCsrfTokenException) {
            message = "CSRF token is missing in the cookies";
        } else if (accessDeniedException instanceof InvalidCsrfTokenException) {
            message = "CSRF token is invalid";
        }

        final ApiError apiError = new ApiError(Collections.singletonList(message), HttpStatus.FORBIDDEN.getReasonPhrase());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        final OutputStream outputStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(outputStream, apiError);
        outputStream.flush();
    }
}
