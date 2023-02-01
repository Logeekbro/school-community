package com.db.dbcommunity.common.exception;

import com.db.dbcommunity.common.api.ResultCode;


public class ApiException extends RuntimeException {
    private ResultCode errorCode;

    public ApiException(ResultCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ResultCode getErrorCode() {
        return errorCode;
    }
}
