package com.db.dbcommunity.auth.feign;

import com.db.dbcommunity.auth.dto.UserAuthDTO;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("school-community-user")
public interface UserFeignClient {


        /**
         * 根据用户名获取用户用于认证的信息（注：此处必须使用RequestParam注解，否则会被转为POST请求）
         * @param username 用户名
         * @return 用户认证信息
         */
        @GetMapping(UserConstant.URI_PREFIX+"/info/userAuth")
        R<UserAuthDTO> getUserAuthByUsername(@RequestParam("username") String username);

        /**
         * 将jti注册到系统中
         */
        @PostMapping(UserConstant.URI_PREFIX+"/login")
        R<Void> login(@RequestParam("jti") String jti, @RequestParam("userId") Long userId);
}
