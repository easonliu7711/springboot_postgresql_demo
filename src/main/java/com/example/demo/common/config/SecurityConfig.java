package com.example.demo.common.config;

import com.example.demo.common.config.convertor.JwtAuthConverter;
import com.example.demo.common.config.handler.CustomAccessDeniedHandler;
import com.example.demo.common.config.handler.CustomAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;
    @Value("${server.api-base-path}")
    private String apiBasePath;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRegistry -> authRegistry
                        .requestMatchers(
                                apiBasePath + "/v1/auth/devices/**",
                                apiBasePath + "/v1/auth/document/**",
                                apiBasePath + "/v1/auth/admin/**").hasRole("SYS_ADMIN_ROLE")
                        .anyRequest().authenticated());
        http
                .exceptionHandling(handlingConfigurer -> handlingConfigurer
                        .authenticationEntryPoint(new CustomAuthEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()));
        http
                .oauth2ResourceServer(oAuth2Configurer -> oAuth2Configurer
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwtAuthConverter))
                        .authenticationEntryPoint(new CustomAuthEntryPoint()));
        http
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(STATELESS));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/",
                apiBasePath + "/v3/api-docs/**",
                apiBasePath + "/swagger-ui/**",
                apiBasePath + "/v1/noauth/**",
                apiBasePath + "/actuator/**");
    }
}
