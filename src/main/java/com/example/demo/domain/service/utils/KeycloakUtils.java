package com.example.demo.domain.service.utils;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.exception.ActionRuntimeException;
import com.example.demo.common.exception.UnauthorizedException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KeycloakUtils {

    @Value("${keycloak-api.server-url}")
    private String keycloakAPIUrl;
    @Resource
    private RestTemplate restTemplate;

    public String genHttpErrorResponseMassage(ResponseEntity<?> responseEntity) {
        return "HttpStatus:" + responseEntity.getStatusCode() + ", ResponseBody:" + responseEntity.getBody();
    }

    private <T> ResponseEntity<T> gettResponseEntity(String apiPath, HttpEntity<?> entity, ResponseEntity<T> responseEntity) {
        ////
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity;
        }
        ////
        if (responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            throw new UnauthorizedException(ErrorCode.ACCESS_TOKEN_INVALID, genHttpErrorResponseMassage(responseEntity));
        }
        ////
        if (responseEntity.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            throw new ActionRuntimeException(ErrorCode.ROLE_PERMISSION_DENIED, genHttpErrorResponseMassage(responseEntity));
        }
        ////
        if (responseEntity.getStatusCode().equals(HttpStatus.CONFLICT)) {
            throw new ActionRuntimeException(ErrorCode.USER_MANAGEMENT_CONFLICT, genHttpErrorResponseMassage(responseEntity));
        }
        ////如果HttpStatus=400
        if (responseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            ////取 keycloak token 如果HttpStatus=400，表示帳號被停用
            if (apiPath.contains("/protocol/openid-connect/token") && entity.getBody() != null) {
                String grantType = ((HttpEntity<MultiValueMap<String, String>>) entity).getBody().get("grant_type").get(0);
                if ("password".equals(grantType)) {
                    throw new ActionRuntimeException(ErrorCode.ACCOUNT_LOCKED, genHttpErrorResponseMassage(responseEntity));
                }
                if ("refresh_token".equals(grantType)) {
                    throw new ActionRuntimeException(ErrorCode.REFRESH_TOKEN_INVALID, genHttpErrorResponseMassage(responseEntity));
                }
            }
            ////
            if (apiPath.contains("/reset-password")) {
                String responseEntityBody = (String) responseEntity.getBody();
                if (responseEntityBody != null && responseEntityBody.contains("invalidPasswordHistoryMessage")) {
                    throw new ActionRuntimeException(ErrorCode.INVALID_SECRET_HISTORY, genHttpErrorResponseMassage(responseEntity));
                }
                throw new ActionRuntimeException(ErrorCode.RESET_SECRET_ERROR, genHttpErrorResponseMassage(responseEntity));
            }
        }
        ////
        throw new ActionRuntimeException(ErrorCode.USER_MANAGEMENT_RESPONSE_ERROR, genHttpErrorResponseMassage(responseEntity));
    }

    public <T> ResponseEntity<T> sendKeycloakRestApi(String apiPath, HttpMethod httpMethod, HttpEntity<?> entity, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(keycloakAPIUrl + apiPath, httpMethod, entity, responseType);
        return gettResponseEntity(apiPath, entity, responseEntity);
    }

    public <T> ResponseEntity<T> sendKeycloakRestApi(String apiPath, HttpMethod httpMethod, HttpEntity<?> entity, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(keycloakAPIUrl + apiPath, httpMethod, entity, responseType);
        return gettResponseEntity(apiPath, entity, responseEntity);
    }
}
