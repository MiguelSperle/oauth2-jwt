package com.example.oauth2.configurations.security;

import com.example.oauth2.configurations.security.authentication.handlers.JwtAccessDeniedHandler;
import com.example.oauth2.configurations.security.authentication.handlers.JwtAuthenticationEntryPointHandler;
import com.example.oauth2.configurations.security.authentication.handlers.OAuth2FailureHandler;
import com.example.oauth2.configurations.security.authentication.handlers.OAuth2SuccessHandler;
import com.example.oauth2.abstractions.JwtService;
import com.example.oauth2.configurations.security.authentication.JwtConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtService jwtService;
    private final JwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/auth/login").permitAll()
                                .requestMatchers("/role").hasRole("USER")
                                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable) // keep this configuration just in development
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth2Login -> {
                    oauth2Login.successHandler(new OAuth2SuccessHandler(this.jwtService));
                    oauth2Login.failureHandler(new OAuth2FailureHandler());
                })
                .oauth2ResourceServer(oauth2ResourceServer -> {
                    oauth2ResourceServer.authenticationEntryPoint(new JwtAuthenticationEntryPointHandler());
                    oauth2ResourceServer.accessDeniedHandler(new JwtAccessDeniedHandler());
                    oauth2ResourceServer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(this.jwtConverter));
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

