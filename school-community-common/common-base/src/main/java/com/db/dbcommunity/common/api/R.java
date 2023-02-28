package com.db.dbcommunity.common.api;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;


@Data
@NoArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = -4153430394359594346L;
    /**
     * 业务状态码
     */
    private Integer code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 接口描述
     */
    private String message;

    /**
     * 全参
     *
     * @param code    业务状态码
     * @param message 描述
     * @param data    结果集
     */
    public R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public R(ResultDetail errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ResultCode.FAILED);
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    /**
     * 成功
     *
     * @return {code:200,message:操作成功,data:自定义}
     */
    public static <T> R<T> success() {
        return new R<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功
     *
     * @param data 结果集
     * @return {code:200,message:操作成功,data:自定义}
     */
    public static <T> R<T> success(T data) {
        return new R<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }


    public static <T> R<T> success(String message) {
        return new R<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功
     *
     * @param data    结果集
     * @param message 自定义提示信息
     * @return {code:200,message:自定义,data:自定义}
     */
    public static <T> R<T> success(T data, String message) {
        return new R<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> R<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     * @return {code:枚举ApiErrorCode取,message:自定义,data:null}
     */
    public static <T> R<T> failed(String message) {
        return new R<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return {code:封装接口取,message:封装接口取,data:null}
     */
    public static <T> R<T> failed(ResultDetail errorCode) {
        return new R<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     * @return {code:枚举ApiErrorCode取,message:自定义,data:null}
     */
    public static <T> R<T> failed(ResultDetail errorCode, String message) {
        return new R<T>(errorCode.getCode(), message, null);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> R<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> R<T> validateFailed(String message) {
        return new R<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> R<T> unauthorized(T data) {
        return new R<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> R<T> forbidden(T data) {
        return new R<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
