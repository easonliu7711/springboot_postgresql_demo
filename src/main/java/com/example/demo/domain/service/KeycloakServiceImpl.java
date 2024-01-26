package com.example.demo.domain.service;

import com.example.demo.domain.service.dto.KeycloakAuthTokenDto;
import com.example.demo.domain.service.dto.KeycloakQueryAuthTokenDto;
import com.example.demo.domain.service.utils.KeycloakUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class KeycloakServiceImpl implements KeycloakService {
    @Value("${keycloak-api.realm}")
    private String keycloakAPIRealm;
    @Value("${keycloak-api.token.client-id}")
    private String keycloakAPIClientId;
    @Value("${keycloak-api.token.secret}")
    private String keycloakAPIClientSecret;
    @Resource
    private KeycloakUtils keycloakUtils;

    @Override
    public KeycloakAuthTokenDto getKeycloakToken(String username, String secret) {
        return getOAuthTokenFromKeycloak(new KeycloakQueryAuthTokenDto(keycloakAPIClientId, keycloakAPIClientSecret, "password", username, secret, ""));
    }

    private KeycloakAuthTokenDto getOAuthTokenFromKeycloak(KeycloakQueryAuthTokenDto keycloakQueryAuthTokenDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", keycloakQueryAuthTokenDto.getGrantType());
        requestBody.add("username", keycloakQueryAuthTokenDto.getUsername());
        requestBody.add("password", keycloakQueryAuthTokenDto.getSecret());
        requestBody.add("client_id", keycloakQueryAuthTokenDto.getClientId());
        requestBody.add("client_secret", keycloakQueryAuthTokenDto.getClientSecret());
        requestBody.add("refresh_token", keycloakQueryAuthTokenDto.getRefreshToken());
        requestBody.add("totp", keycloakQueryAuthTokenDto.getTotp());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<KeycloakAuthTokenDto> response = keycloakUtils.sendKeycloakRestApi("/realms/" + keycloakAPIRealm + "/protocol/openid-connect/token", HttpMethod.POST, entity, KeycloakAuthTokenDto.class);
        return response.getBody();
    }

    @Override
    public KeycloakAuthTokenDto refreshToken(String refreshToken) {
        return getOAuthTokenFromKeycloak(new KeycloakQueryAuthTokenDto(keycloakAPIClientId, keycloakAPIClientSecret, "refresh_token", refreshToken));
    }
}
