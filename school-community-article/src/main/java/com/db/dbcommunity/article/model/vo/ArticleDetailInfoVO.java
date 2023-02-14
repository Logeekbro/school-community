package com.db.dbcommunity.article.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章的详细信息
 */
@Data
public class ArticleDetailInfoVO implements Serializable {
    /**
     * 主键
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 分区id
     */
    private Integer sectionId;

    /**
     * 文章封面图片
     */
    private String mainPic;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 创建时间
     */
    private Date createTime;


}
