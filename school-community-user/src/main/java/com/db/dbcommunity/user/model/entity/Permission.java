package com.db.dbcommunity.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import com.db.dbcommunity.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 权限表
 * @TableName permission
 */
@TableName(value ="permission")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
public class Permission extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 所属分组ID

     */
    private Integer groupId;

    /**
     * URL权限标识
     */
    private String urlPerm;


    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 按钮标识
     */
    private String btnSign;

    /**
     * 有权限的角色编号集合
     */
    @TableField(exist = false)
    private List<String> roles;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Permission(Long id,String name, Integer groupId, String urlPerm) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.urlPerm = urlPerm;
    }
}