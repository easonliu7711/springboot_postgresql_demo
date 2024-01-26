package com.example.demo.domain.service;

import com.example.demo.domain.service.dto.KeycloakAuthTokenDto;

public interface KeycloakService {
    KeycloakAuthTokenDto getKeycloakToken(String email, String secret);

    KeycloakAuthTokenDto refreshToken(String cookieValue);
}
