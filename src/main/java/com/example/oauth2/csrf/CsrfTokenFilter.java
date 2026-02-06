package com.example.oauth2.csrf;

import com.example.oauth2.exceptions.CsrfTokenRequiredException;
import com.example.oauth2.exceptions.InvalidCsrfTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsrfTokenFilter extends OncePerRequestFilter {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            log.info("Processing csrf token filter");

            final HandlerMethod handlerMethod = this.getHandlerMethod(request);

            if (handlerMethod != null && this.isCsrfTokenAnnotated(handlerMethod)) {
                final String csrfTokenHeader = request.getHeader(CsrfToken.CSRF_TOKEN_HEADER);

                if (csrfTokenHeader == null || csrfTokenHeader.isEmpty()) {
                    throw CsrfTokenRequiredException.with("Csrf token is required and the required header is 'x-csrf-token'");
                }

                final String csrfTokenCookie = this.getCsrfTokenCookie(request);

                if (!csrfTokenHeader.equals(csrfTokenCookie)) {
                    throw InvalidCsrfTokenException.with("Invalid csrf token");
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            this.handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private HandlerMethod getHandlerMethod(HttpServletRequest request) {
        final HandlerExecutionChain handlerChain;

        try {
            handlerChain = this.requestMappingHandlerMapping.getHandler(request);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod) {
            return (HandlerMethod) handlerChain.getHandler();
        }

        return null;
    }

    private boolean isCsrfTokenAnnotated(HandlerMethod handlerMethod) {
        final Method method = handlerMethod.getMethod();
        return method.isAnnotationPresent(CsrfToken.class) && handlerMethod.getBeanType().isAnnotationPresent(RestController.class);
    }

    private String getCsrfTokenCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> CsrfToken.CSRF_TOKEN_COOKIE.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
