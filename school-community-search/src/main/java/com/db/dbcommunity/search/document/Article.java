package com.db.dbcommunity.search.document;

import lombok.Data;

import java.util.Date;

@Data
public class Article implements EsDocument {

    private Long articleId;

    private Long authorId;

    private String title;

    private String summary;

    private String mainPic;

    private Long viewCount;

    private Long likeCount;

    private Date createTime;

    @Override
    public String id() {
        return this.articleId.toString();
    }
}
