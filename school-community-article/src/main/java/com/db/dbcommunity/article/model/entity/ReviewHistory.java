package com.db.dbcommunity.article.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_review_history
 */
@TableName(value ="tb_review_history")
@Data
public class ReviewHistory implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 被审核的文章id
     */
    private Long articleId;

    /**
     * 审核这篇文章的管理员用户id
     */
    private Long userId;

    /**
     * 是否审核通过 0-未通过，1-通过
     */
    private Boolean isPass;

    /**
     * 本次审核的描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date updateTime;

    /**
     * 逻辑删除，0-未删除 1-已删除
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}