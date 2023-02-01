package com.db.dbcommunity.common.exception;


import com.db.dbcommunity.common.api.ResultCode;

public class ApiAsserts {
    /**
     * 抛失败异常
     *
     * @param message 说明
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * 抛失败异常
     *
     * @param errorCode 状态码
     */
    public static void fail(ResultCode errorCode) {
        throw new ApiException(errorCode);
    }
}
