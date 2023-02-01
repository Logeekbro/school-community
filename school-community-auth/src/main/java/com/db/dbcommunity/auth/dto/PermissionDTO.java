package com.db.dbcommunity.auth.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.db.dbcommunity.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionDTO extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 菜单模块ID

     */
    private Integer menuId;

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
}
