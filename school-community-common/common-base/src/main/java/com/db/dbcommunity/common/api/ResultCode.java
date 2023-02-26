package com.db.dbcommunity.common.api;


public enum ResultCode implements ResultDetail {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 失败
     */
    FAILED(-1, "操作失败"),
    /**
     * 未登录，Token过期
     */
    UNAUTHORIZED(401, "请先登录"),
    /**
     * 权限不足
     */
    FORBIDDEN(402, "权限不足"),
    /**
     * 参数校验错误
     */
    VALIDATE_FAILED(403, "参数检验失败"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 账号封禁中
     */
    BANED(601, "账号封禁中"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(602, "用户不存在"),

    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(603, "用户名或密码错误"),

    /**
     * 未授权访问
     */
    ACCESS_UNAUTHORIZED(701, "未授权访问"),

    /**
     * token有误或已过期
     */
    TOKEN_INVALID_OR_EXPIRED(702, "token有误或已过期"),
    /**
     * 访问被拒绝
     */
    TOKEN_ACCESS_FORBIDDEN(703, "访问被拒绝"),
    /**
     * 客户端认证失败
     */
    CLIENT_AUTHENTICATION_FAILED(704, "客户端认证失败"),

    /**
     * 请求不安全
     */
    UN_SECURITY_REQUEST(801, "请求不安全");


    private final Integer code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
