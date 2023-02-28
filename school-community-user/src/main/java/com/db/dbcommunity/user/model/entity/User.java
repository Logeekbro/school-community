package com.db.dbcommunity.user.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户真名
     */
    private String realName;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * 用户自我介绍
     */
    private String introduce;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户入校年份
     */
    private Integer enterSchoolYear;

    /**
     * 用户的 专业id/任职学院id
     */
    private Object majorId;

    /**
     * 用户的 学生学号/教师工号
     */
    private Object schoolId;

    /**
     * 用户状态：0-正常/1-封禁中/...
     */
    private Integer status;

    /**
     * 用户创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 用户信息更新时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date updateTime;

    /**
     * 逻辑删除 0-未删除，1-已删除
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}