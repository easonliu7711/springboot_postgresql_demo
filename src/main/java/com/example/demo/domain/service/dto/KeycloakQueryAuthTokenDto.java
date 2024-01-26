package com.example.demo.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KeycloakQueryAuthTokenDto {
    @Schema(description = "客戶端ID")
    @JsonProperty("client_id")
    private String clientId;
    @Schema(description = "客戶端Secret")
    @JsonProperty("client_secret")
    private String clientSecret;
    @Schema(description = "授權類型")
    @JsonProperty("grant_type")
    private String grantType;
    @Schema(description = "客戶端密碼")
    @JsonProperty("secret")
    private String secret;
    @Schema(description = "刷新令牌")
    @JsonProperty("refresh_token")
    private String refreshToken;
    @Schema(description = "TOTP")
    @JsonProperty("totp")
    private String totp;
    @Schema(description = "用戶名稱")
    @JsonProperty("username")
    private String username;

    public KeycloakQueryAuthTokenDto(String keycloakAPIClientId, String keycloakAPIClientSecret, String grantType, String username, String secret, String totp) {
        this.clientId = keycloakAPIClientId;
        this.clientSecret = keycloakAPIClientSecret;
        this.grantType = grantType;
        this.secret = secret;
        this.username = username;
        this.totp = totp;
    }

    public KeycloakQueryAuthTokenDto(String keycloakAPIClientId, String keycloakAPIClientSecret, String grantType, String refreshToken) {
        this.clientId = keycloakAPIClientId;
        this.clientSecret = keycloakAPIClientSecret;
        this.grantType = grantType;
        this.refreshToken = refreshToken;
    }
}
