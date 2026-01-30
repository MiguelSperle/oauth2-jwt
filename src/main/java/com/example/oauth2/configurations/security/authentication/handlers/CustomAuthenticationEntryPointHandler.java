package com.example.oauth2.configurations.security.authentication.handlers;

import com.example.oauth2.utils.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        String message = "Authentication failed";

        if (authenticationException instanceof InsufficientAuthenticationException) {
            message = "Access token is required";
        }

        final Throwable cause = authenticationException.getCause();

        if (cause instanceof JwtValidationException) {
            message = "Access token is expired";
        } else if (cause instanceof BadJwtException) {
            message = "Access token is invalid";
        }

        final ApiError apiError = new ApiError(Collections.singletonList(message), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        final OutputStream outputStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(outputStream, apiError);
        outputStream.flush();
    }
}
