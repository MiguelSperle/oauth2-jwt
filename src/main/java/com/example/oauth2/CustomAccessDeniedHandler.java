package com.example.oauth2;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        final String message = "Access denied. You don't have permission to access this resource";
        final ApiError apiError = new ApiError(Collections.singletonList(message), HttpStatus.FORBIDDEN.getReasonPhrase());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        final OutputStream responseStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(responseStream, apiError);
        responseStream.flush();
    }
}
