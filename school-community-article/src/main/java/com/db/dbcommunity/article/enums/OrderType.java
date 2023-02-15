package com.db.dbcommunity.article.enums;

/**
 * 字段的排序类型
 */
public enum OrderType {
    CREATE_TIME_DESC("create_time", "DESC"),
    VIEW_COUNT_DESC("view_count", "DESC");

    private final String field;

    private final String type;


    OrderType(String field, String type) {
        this.field = field;
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public String getType() {
        return type;
    }
}
