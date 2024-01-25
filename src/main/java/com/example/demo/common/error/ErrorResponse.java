package com.example.demo.common.error;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ErrorResponse {

    private final String errorCode;

    private final String errorMsg;

    private final String errorDetail;

    private final LocalDateTime timestamp;

    private ErrorResponse(String errorCode, String errorMsg, String errDetail, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorDetail = errDetail;
        this.timestamp = timestamp;
    }

    public static ErrorResponse unknown(String errorMsg) {
        return ErrorResponse.of(ErrorCode.UNKNOWN_ERROR_OCCURRED, errorMsg);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(String.valueOf(errorCode.getCode()), errorCode.getMessage(), errorCode.getReason(), LocalDateTime.now());
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(String.valueOf(errorCode.getCode()), errorCode.getMessage(), message, LocalDateTime.now());
    }
}
