package com.db.dbcommunity.user.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserLoginController {

    @Resource
    private UserService userService;

    /**
     * 将token中的jti注册到系统中
     */
    @PostMapping("/login")
    public R<Void> login(@RequestParam String jti, @RequestParam Long userId) {
        return userService.login(jti, userId) ? R.success() : R.failed();
    }

    // TODO 方案完善中
    @PostMapping("/register")
    public R register() {
        return null;
    }

    /**
     * 退出登录
     * @return
     */
    @DeleteMapping("/logout")
    public R<Void> logout() {
        return userService.logout(UserContext.getCurrentUserJti(), UserContext.getCurrentUserId()) ? R.success() : R.failed();
    }
}
