package com.example.oauth2.csrf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CsrfToken {
    String CSRF_TOKEN_HEADER = "x-csrf-token";
    String CSRF_TOKEN_COOKIE = "csrf-token";
}
