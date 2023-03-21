package com.db.dbcommunity.article.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户主页中发表的文章信息
 */
@Data
public class UserHomePageArticleInfoVO implements Serializable {

    private String articleId;

    private String title;

    private Date createTime;

    private Date updateTime;
}
