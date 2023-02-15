package com.db.dbcommunity.article.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章列表页需要的文章信息
 */
@Data
public class ArticleMainInfoVO implements Serializable {

    private Long articleId;

    private Long authorId;

    private String summary;

    private String mainPic;

    private Date createTime;

}
