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

/**
 * 
 * @TableName tb_comment
 */
@TableName(value ="tb_comment")
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity implements Serializable {
    /**
     * 主键，评论id
     */
    @TableId(type = IdType.AUTO)
    private Long commentId;

    /**
     * 评论者id
     */
    private Long userId;

    /**
     * 被评论的文章id
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 逻辑删除， 0-未删除  1-已删除
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}