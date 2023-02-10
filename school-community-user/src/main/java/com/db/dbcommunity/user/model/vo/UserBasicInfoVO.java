package com.db.dbcommunity.user.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户的基本信息VO，
 * userId, username, avatar, nickName
 */
@Data
public class UserBasicInfoVO implements Serializable {

    private Long userId;

    private String username;

    private String nickName;

    private String avatar;

}
