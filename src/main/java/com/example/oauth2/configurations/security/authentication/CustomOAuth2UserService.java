package com.example.oauth2.configurations.security.authentication;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        final OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        // 🔹 here you can search/create the user in the database

        final String userId = UUID.randomUUID().toString();

        return new CustomOAuth2User(oAuth2User, userId);
    }
}
