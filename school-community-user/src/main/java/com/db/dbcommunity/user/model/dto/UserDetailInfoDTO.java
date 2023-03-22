package com.db.dbcommunity.user.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDetailInfoDTO {

    private Long userId;

    @NotEmpty(message = "昵称不能为空")
    private String nickName;

    @Email(message = "请输入正确的邮箱格式")
    private String email;

    @Size(min = 11, max = 11, message = "必须为11位标准手机号")
    private String phone;

    private String introduce;
}
