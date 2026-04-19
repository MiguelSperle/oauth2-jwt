package com.example.oauth2.helpers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;

@Component
public class HandlerMethodHelper {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public HandlerMethodHelper(final RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    public HandlerMethod getHandlerMethod(final HttpServletRequest request) {
        final HandlerExecutionChain handlerChain;

        try {
            handlerChain = this.requestMappingHandlerMapping.getHandler(request);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }

        if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod) {
            return (HandlerMethod) handlerChain.getHandler();
        }

        return null;
    }

    public boolean isAnnotated(final HandlerMethod handlerMethod, final Class<? extends Annotation> annotation) {
        return handlerMethod.getMethod().isAnnotationPresent(annotation) && handlerMethod.getBeanType().isAnnotationPresent(RestController.class);
    }
}
