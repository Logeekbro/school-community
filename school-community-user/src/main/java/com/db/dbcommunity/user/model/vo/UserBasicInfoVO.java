package com.db.dbcommunity.user.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户的基本信息VO，
 * userId, username, avatar, nickName, createTime
 */
@Data
public class UserBasicInfoVO implements Serializable {

    private Long userId;

    private String username;

    private String nickName;

    private String avatar;

    private String introduce;

    private Date createTime;

}
