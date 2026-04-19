package com.example.oauth2.configurations.security.web.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebCorsConfiguration {
    @Value("${app.security.web.cors.allowed-origin}")
    private String allowedOrigin;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(this.allowedOrigin); // Allow access only to this domain.
        corsConfiguration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()));
        corsConfiguration.setAllowedHeaders(List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT, "x-csrf-token"));
        corsConfiguration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }
}
