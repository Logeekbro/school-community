package com.db.dbcommunity.thirdparty.feign;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("school-community-user")
public interface UserFeignClient {

    @PutMapping(UserConstant.URI_PREFIX + "/info/internal")
    R<Void> updateUserDetailInfo(@RequestBody Map<String, Object> map);
}
