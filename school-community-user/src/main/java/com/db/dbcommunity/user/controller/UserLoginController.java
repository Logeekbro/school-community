package com.db.dbcommunity.user.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.user.model.vo.UserRegisterVO;
import com.db.dbcommunity.user.service.UserService;
import org.springframework.validation.annotation.Validated;
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


    /**
     * 账号注册
     */
    @PostMapping("/register")
    public R<Void> register(@RequestBody @Validated UserRegisterVO vo) {
        return userService.register(vo) ? R.success("注册成功") : R.failed("注册失败");
    }

    /**
     * 退出登录
     */
    @DeleteMapping(value = "/logout", name = "退出登录_true")
    public R<Void> logout() {
        return userService.logout(UserContext.getCurrentUserJti(), UserContext.getCurrentUserId()) ? R.success() : R.failed();
    }

    /**
     * AdminAPI
     * 封禁用户
     */
    @PutMapping(value = "/ban/{userId}", name = "封禁用户_f")
    public R<Void> ban(@PathVariable Long userId) {
        return userService.ban(userId) ? R.success() : R.failed();
    }

}
