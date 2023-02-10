package com.db.dbcommunity.user.model.vo;

import lombok.Data;

@Data
public class PermissionUpdateVO {
    private Long id;
    private String name;
    private String urlPerm;
    private Integer groupId;
}
