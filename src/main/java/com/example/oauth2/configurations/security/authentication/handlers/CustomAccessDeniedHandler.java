package com.example.oauth2.configurations.security.authentication.handlers;

import com.example.oauth2.configurations.json.Json;
import com.example.oauth2.utils.ApiError;
import com.example.oauth2.utils.InstantUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final AccessDeniedException ex
    ) throws IOException {
        final ApiError apiError = ApiError.from("Access denied", InstantUtils.now());

        response.getWriter().write(Json.writeValueAsString(apiError));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
