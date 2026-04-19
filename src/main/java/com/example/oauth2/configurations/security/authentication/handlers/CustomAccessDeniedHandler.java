package com.example.oauth2.configurations.security.authentication.handlers;

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
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final AccessDeniedException ex
    ) throws IOException {
        final String message = "Access denied";

        final ApiError apiError = new ApiError(Collections.singletonList(message), InstantUtils.now());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        final OutputStream outputStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(outputStream, apiError);
        outputStream.flush();
    }
}
