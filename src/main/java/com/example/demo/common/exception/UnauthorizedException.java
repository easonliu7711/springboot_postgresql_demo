package com.example.demo.common.exception;

import com.example.demo.common.error.ErrorCode;

public class UnauthorizedException extends ActionRuntimeException {

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
