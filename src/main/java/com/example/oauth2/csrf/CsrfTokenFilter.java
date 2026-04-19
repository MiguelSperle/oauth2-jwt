package com.example.oauth2.csrf;

import com.example.oauth2.exceptions.CsrfTokenRequiredException;
import com.example.oauth2.exceptions.InvalidCsrfTokenException;
import com.example.oauth2.helpers.HandlerMethodHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;

@Component
public class CsrfTokenFilter extends OncePerRequestFilter {
    private final HandlerMethodHelper handlerMethodHelper;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public CsrfTokenFilter(
            final HandlerMethodHelper handlerMethodHelper,
            final HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.handlerMethodHelper = handlerMethodHelper;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) {
        try {
            final HandlerMethod handlerMethod = this.handlerMethodHelper.getHandlerMethod(request);

            if (handlerMethod != null && this.handlerMethodHelper.isAnnotated(handlerMethod, CsrfToken.class)) {
                final String csrfTokenHeader = request.getHeader(CsrfToken.CSRF_TOKEN_HEADER);

                if (csrfTokenHeader == null || csrfTokenHeader.isBlank()) {
                    throw new CsrfTokenRequiredException("Csrf token is required and the required header is 'x-csrf-token'");
                }

                final String csrfTokenCookie = this.getCsrfTokenCookie(request);

                if (!csrfTokenHeader.equals(csrfTokenCookie)) {
                    throw new InvalidCsrfTokenException("Invalid csrf token");
                }
            }

            filterChain.doFilter(request, response);
        } catch (final Exception ex) {
            this.handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private String getCsrfTokenCookie(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(CsrfToken.CSRF_TOKEN_COOKIE))
                .findFirst().map(Cookie::getValue).orElse(null);
    }
}
