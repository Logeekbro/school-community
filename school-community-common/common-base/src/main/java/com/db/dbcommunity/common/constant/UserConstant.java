package com.db.dbcommunity.common.constant;

public interface UserConstant {
    // 用户服务的访问路径前缀
    String URI_PREFIX = "/user";

    // 普通用户的角色id
    Integer DEFAULT_USER_ROLE_ID = 10;

    // 每个账号允许同时登录的客户端数量
    short MAX_CLIENT_COUNT = 5;
}
