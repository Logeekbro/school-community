package com.db.dbcommunity.common.constant;

import java.util.concurrent.TimeUnit;

public interface SecurityConstant {
    // 安全认证服务的访问路径前缀
    String URI_PREFIX = "/security";

    // 安全凭证的请求头名称
    String VERIFY_TOKEN_KEY = "VerifyToken";

    // 存储在redis中的verifyToken前缀
    String CACHE_VERIFY_TOKEN_PREFIX = "security:verify:token:";

    // 验证通过后生成的accessToken有效时间
    Integer VERIFY_TOKEN_EXPIRE_TIME = 30;

    // 有效时间的单位
    TimeUnit EXPIRE_UNIT = TimeUnit.DAYS;
}
