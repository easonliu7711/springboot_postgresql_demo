package com.example.demo.common.error;

import lombok.Getter;

/**
 * Enumeration of API Error codes.
 */
@Getter
public enum ErrorCode {
    SUCCESS("00000", "Success", "成功"),

    //---------------------------------------
    // [1xxxx] Authentication and Permission
    //---------------------------------------

    ACCESS_TOKEN_INVALID("10001", "Access Token Invalid", "帳號密碼錯誤"),
    ACCOUNT_LOCKED("10002", "Account Locked", "帳戶被鎖定"),
    ROLE_PERMISSION_DENIED("10003", "Role Permission Denied", "角色權限被拒絕"),
    REFRESH_TOKEN_INVALID("10004", "Refresh Token Invalid", "Refresh Token 失效"),
    ACCESS_TOKEN_GET_INFO_FAILED("10005", "Failed to get access token information", "獲取 Access Token 信息失敗"),


    //---------------------------------------
    // [2xxxx] User Management
    //---------------------------------------

    USER_MANAGEMENT_CONFLICT("20001", "User Management Conflict", "新增使用者管理相關帳號或角色群組發生衝突"),
    UPDATE_SECRET_EMAIL_SEND_FAILED("20002", "Update Secret Email Send Failed", "更新密碼信件發送失敗，請再試一次"),
    RESET_SECRET_ERROR("20003", "Reset Secret Error", "密碼請輸入長度至少8碼且包含英數字大小寫各一字元且不能與前5次密碼相同"),
    INVALID_SECRET_HISTORY("20004", "Invalid Secret History", "密碼不能與前5次密碼相同"),
    USER_MANAGEMENT_RESPONSE_ERROR("29999", "User Management Response Error", "使用者管理錯誤"),

    //---------------------------------------
    // [3xxxx] Document Management
    //---------------------------------------
    DOCUMENT_PERMISSION_DENIED("30001", "Document Permission Denied", "文件權限被拒絕"),
    //---------------------------------------
    // [9xxxx] Runtime
    //---------------------------------------

    MISSING_REQUEST_INPUT("90001", "Missing Request Parameter", "缺少請求參數"),
    HTTP_MESSAGE_NOT_READABLE("90002", "Http Message Not Readable", "Http 訊息不可讀"),
    METHOD_ARGUMENT_NOT_VALID("90003", "Method Argument Not Valid", "方法參數無效"),
    CACHE_REFRESH_FAILED("90004", "Cache Refresh Failed", "Cache 更新失敗"),
    UNKNOWN_ERROR_OCCURRED("99999", "Unknown Error Occurred", "發生未知錯誤"),
    ;

    private final String code;
    private final String reason;
    private final String message;

    ErrorCode(String code, String reason, String message) {
        this.code = code;
        this.reason = reason;
        this.message = message;
    }

    @Override
    public String toString() {
        return this.code + " " + name();
    }
}
