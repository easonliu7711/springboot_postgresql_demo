package com.example.demo.application.controller;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.exception.UnauthorizedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public abstract class BaseController {

    @Getter
    private ApplicationContext context;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    protected String getActionUserId() {
        if (getAuthentication() == null) {
            throw new UnauthorizedException(ErrorCode.ACCESS_TOKEN_GET_INFO_FAILED, "無法取得 AccessToken 對應 User Id");
        }
        return getAuthentication().getName();
    }

    protected String getAuthToken() {
        try {
            String token = getHttpServletRequest().getHeader("Authorization");
            return token.replace("Bearer ", "");
        } catch (Exception ex) {
            throw new UnauthorizedException(ErrorCode.ACCESS_TOKEN_GET_INFO_FAILED, "[Keycloak]Get Auth Token Error");
        }
    }

    protected JwtAuthenticationToken getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    protected String getCookieValue(String cookieName) {
        Cookie[] cookies = getHttpServletRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    protected HttpServletRequest getHttpServletRequest() {
        return this.httpServletRequest;
    }

    @Autowired
    protected void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    protected HttpServletResponse getHttpServletResponse() {
        return this.httpServletResponse;
    }

    @Autowired
    protected void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    protected String getRefreshToken() {
        Cookie[] cookies = getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if ("refresh_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    protected String getUserRoleByToken() {
        if (getAuthentication() == null) {
            throw new UnauthorizedException(ErrorCode.ACCESS_TOKEN_GET_INFO_FAILED, "Get Role Error");
        }
        Collection<GrantedAuthority> roles = getAuthentication().getAuthorities();
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().contains("ROLE_BMS_")) {
                return role.getAuthority().replace("ROLE_", "");
            }
        }
        throw new UnauthorizedException(ErrorCode.ACCESS_TOKEN_GET_INFO_FAILED, "Get Role Error");
    }

    @Autowired
    protected void setContext(ApplicationContext context) {
        this.context = context;
    }

}
