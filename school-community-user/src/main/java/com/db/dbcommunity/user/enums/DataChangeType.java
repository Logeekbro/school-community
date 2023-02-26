package com.db.dbcommunity.user.enums;

public enum DataChangeType {

    UPDATE_PASSWORD(0, "修改密码"),
    BAN_USER(1, "封禁用户");

    private final int code;
    private final String desc;

    DataChangeType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
