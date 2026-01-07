package com.example.oauth2.configurations.security.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User oAuth2User;
    private final String userId;

    public CustomOAuth2User(OAuth2User oAuth2User, String userId) {
        this.oAuth2User = oAuth2User;
        this.userId = userId;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return this.oAuth2User.getName();
    }

    public String getUserId() {
        return this.userId;
    }
}
