package com.example.demo.application.controller;

import com.example.demo.application.controller.request.LoginRequest;
import com.example.demo.domain.service.KeycloakService;
import com.example.demo.domain.service.dto.KeycloakAuthTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("${server.api-base-path}")
@Validated
public class AuthController extends BaseController{

    @Resource
    private KeycloakService keycloakService;

    protected void addResponseHeader(String value) {
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", URLEncoder.encode(value, StandardCharsets.UTF_8))
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .build();
        getHttpServletResponse().addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    @Operation(summary = "獲取 AuthToken", description = "獲取 AuthToken")
    @PostMapping("/v1/noauth/token")
    public String getAuthToken(@Valid @RequestBody LoginRequest loginRequest) {
        KeycloakAuthTokenDto keycloakAuthTokenDto = keycloakService.getKeycloakToken(loginRequest.getEmail(), loginRequest.getSecret());
        addResponseHeader(keycloakAuthTokenDto.getRefreshToken());
        return "Bearer " + keycloakAuthTokenDto.getAccessToken();
    }

    protected String getCookieValue() {
        Cookie[] cookies = getHttpServletRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                }
            }
        }
        return null;
    }

    @Operation(summary = "更新 AuthToken", description = "更新 AuthToken")
    @PostMapping("/v1/noauth/token/refresh")
    public String refreshAuthToken() {
        KeycloakAuthTokenDto keycloakAuthTokenDto = keycloakService.refreshToken(getCookieValue());
        addResponseHeader(keycloakAuthTokenDto.getRefreshToken());
        return "Bearer " + keycloakAuthTokenDto.getAccessToken();
    }
}
