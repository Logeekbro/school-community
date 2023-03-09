package com.db.dbcommunity.article.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_section
 */
@TableName(value ="tb_section")
@Data
public class Section implements Serializable {
    /**
     * 主键，分区id
     */
    @TableId(type = IdType.AUTO)
    private Integer sectionId;

    /**
     * 分区名称
     */
    private String sectionName;

    /**
     * 分区的创建者id
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 逻辑删除， 0-未删除 1-已删除
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}