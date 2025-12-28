package com.example.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        final Throwable cause = ex.getCause();

        String message;
        if (cause instanceof AuthorizationDeniedException) {
            message = "JWT token is required to access this resource";
        } else if (cause instanceof BadJwtException) {
            message = "JWT token is invalid";
        } else {
            message = "Unknown error";
        }

        final ApiError apiError = new ApiError(Collections.singletonList(message), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final OutputStream responseStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(responseStream, apiError);
        responseStream.flush();
    }
}
