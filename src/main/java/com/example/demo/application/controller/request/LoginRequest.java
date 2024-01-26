package com.example.demo.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(description = "email")
    @NotEmpty
    private String email;
    @Schema(description = "secret")
    @NotEmpty
    private String secret;

    @Override
    public String toString() {
        return "LoginRequest(" +
                "email=" + email +
                ", secret=*****" +
                ")";
    }
}
