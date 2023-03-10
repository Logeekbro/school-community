package com.db.dbcommunity.article.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.db.dbcommunity.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName tb_article
 */
@TableName(value ="tb_article")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Article extends BaseEntity implements Serializable {
    /**
     * 主键，文章id
     */
    @TableId(type = IdType.AUTO)
    private Long articleId;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章封面图
     */
    private String mainPic;

    /**
     * 文章所属分区的id
     */
    private Integer sectionId;

    /**
     * 是否置顶，0-不置顶 1-置顶 默认0
     */
    private Boolean top;

    /**
     * 文章状态 0-正常 1-审核中 2-审核不通过 3-新发布且还未被管理员查看的文章
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Long viewCount;


    /**
     * 逻辑删除， 0-未删除  1-已删除
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}