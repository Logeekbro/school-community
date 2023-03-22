package com.db.dbcommunity.user.model.vo;

import lombok.Data;

@Data
public class UserDetailInfoVO {

    private Long userId;

    private String username;

    private String nickName;

    private String email;

    private String phone;

    private String introduce;
}
