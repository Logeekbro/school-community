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
 * @TableName tb_tag
 */
@TableName(value ="tb_tag")
@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity implements Serializable {
    /**
     * 主键，标签名称
     */
    @TableId
    private String tagName;

    /**
     * 逻辑删除 0-未删除 1-已删除
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}