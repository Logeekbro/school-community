package com.db.dbcommunity.user.model.vo;

import lombok.Data;

@Data
public class PermissionCreateVO {

    private String name;
    private String urlPerm;
    private Integer groupId;
    private Boolean isUserDefault;
}
