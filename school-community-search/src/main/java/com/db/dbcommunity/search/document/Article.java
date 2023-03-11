package com.db.dbcommunity.search.document;

import lombok.Data;

import java.util.Date;

@Data
public class Article implements EsDocument {

    private Long articleId;

    private String title;

    private String summary;

    private Long viewCount;

    private Long likeCount;

    private Date createTime;

    @Override
    public String id() {
        return this.articleId.toString();
    }
}
