package com.db.dbcommunity.article.model.vo;

import lombok.Data;

/**
 * 作者id及其发表的文章数量
 */
@Data
public class AuthorIdWithArticleCountVO {

    private Long authorId;
    private Long articleCount;
}
