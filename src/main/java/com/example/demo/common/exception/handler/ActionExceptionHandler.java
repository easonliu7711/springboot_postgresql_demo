package com.example.demo.common.exception.handler;


import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.error.ErrorResponse;
import com.example.demo.common.exception.ActionRuntimeException;
import com.example.demo.common.logging.LoggingHelper;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;


@ControllerAdvice
class ActionExceptionHandler extends ResponseEntityExceptionHandler {

    private String getMethodArgumentNotValidMessage(MethodArgumentNotValidException ex) {
        BindingResult exceptions = ex.getBindingResult();
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                FieldError fieldError = (FieldError) errors.get(0);
                return fieldError.getField() + " " + fieldError.getDefaultMessage();
            }
        }
        return ex.toString();
    }

    /**
     * Respond with HTTP 200 Success for business exception
     *
     * @param e ActionRuntimeException
     * @return ResponseEntity
     */
    @ExceptionHandler(ActionRuntimeException.class)
    public ResponseEntity<Object> handleActionRuntimeException(ActionRuntimeException e) {
        return ResponseEntity.ok(ErrorResponse.of(e.getErrorCode(), e.getMessage()));
    }

    /**
     * Respond with HTTP 200 Success for constraint violation exception
     *
     * @param e ConstraintViolationException
     * @return ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_ARGUMENT_NOT_VALID, e.getMessage());
        LoggingHelper.toRequestInvalidLog(request, errorResponse);
        return ResponseEntity.ok(errorResponse);
    }

    /**
     * Request異常
     *
     * @param ex 參數異常
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @Nonnull HttpHeaders headers, @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.HTTP_MESSAGE_NOT_READABLE, ex.getMessage());
        LoggingHelper.toRequestInvalidLog(request, errorResponse);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * 參數驗證異常
     *
     * @param ex 参数驗證異常
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@Nonnull MethodArgumentNotValidException ex, @Nonnull HttpHeaders headers, @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_ARGUMENT_NOT_VALID, getMethodArgumentNotValidMessage(ex));
        LoggingHelper.toRequestInvalidLog(request, errorResponse);
        return ResponseEntity.ok(errorResponse);
    }

    /**
     * Request參數異常
     *
     * @param ex 參數異常
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, @Nonnull HttpHeaders headers, @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.MISSING_REQUEST_INPUT, ex.getMessage());
        LoggingHelper.toRequestInvalidLog(request, errorResponse);
        return ResponseEntity.ok(errorResponse);
    }

    /**
     * Request參數轉換異常
     *
     * @param ex 參數異常
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, @Nonnull HttpHeaders headers, @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_ARGUMENT_NOT_VALID, ex.getMessage());
        LoggingHelper.toRequestInvalidLog(request, errorResponse);
        return ResponseEntity.ok(errorResponse);
    }

    /**
     * 沒有被處理到的Exception都會跑來這
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleThrowable(Throwable t) {
        return ResponseEntity.ok(ErrorResponse.unknown(t.getMessage()));
    }
}