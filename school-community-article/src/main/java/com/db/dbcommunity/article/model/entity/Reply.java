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
 * @TableName tb_reply
 */
@TableName(value ="tb_reply")
@Data
@EqualsAndHashCode(callSuper = true)
public class Reply extends BaseEntity implements Serializable {
    /**
     * 主键，回复id
     */
    @TableId
    private Long replyId;

    /**
     * 回复者id
     */
    private Long userId;

    /**
     * 回复的目标用户id，如果为空则回复评论
     */
    private Long target;

    /**
     * 回复的评论id
     */
    private Long commentId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 逻辑删除， 0-未删除  1-已删除
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}