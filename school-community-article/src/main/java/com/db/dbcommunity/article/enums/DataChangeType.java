package com.db.dbcommunity.article.enums;

/**
 * 改变数据的操作类型
 */
public enum DataChangeType {

    INSERT_ARTICLE(0, "新增文章"),
    UPDATE_ARTICLE(1, "更新文章"),
    DELETE_ARTICLE(2, "删除文章"),
    ARTICLE_PASS_REVIEW(3, "文章通过审核"),
    ARTICLE_NOT_PASS_REVIEW(4, "文章未通过审核");

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
