package com.db.dbcommunity.visit.model.entity;

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
 * @TableName tb_history
 */
@TableName(value ="tb_history")
@Data
@EqualsAndHashCode(callSuper = false)
public class History extends BaseEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long historyId;

    /**
     * 这条历史记录所属的用户id
     */
    private Long userId;

    /**
     * 这条历史记录所属的文章id
     */
    private Long articleId;

    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}