package com.db.dbcommunity.user.model.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserRegisterVO {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @Size(min = 6, max = 20, message = "密码长度在6-20之间")
    private String password;

    @Email(message = "请输入正确的邮箱")
    private String email;

    private String nickName;
}
