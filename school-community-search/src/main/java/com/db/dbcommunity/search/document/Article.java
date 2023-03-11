package com.db.dbcommunity.search.document;

import lombok.Data;

@Data
public class Article implements EsDocument {

    private Long articleId;

    private String title;

    private String summary;

    @Override
    public String id() {
        return this.articleId.toString();
    }
}
