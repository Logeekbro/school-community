package com.db.dbcommunity.visit.feign;

import com.db.dbcommunity.common.constant.UserConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("school-community-user")
public interface UserPermissionFeignClient {

    String prefix = UserConstant.URI_PREFIX + "/permission";

    @PostMapping(prefix + "/")
    void addPermission(@RequestBody Map<String, Object> map);
}
