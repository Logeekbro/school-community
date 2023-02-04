package com.db.dbcommunity.user.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/info")
public class UserInfoController {

    @Resource
    private UserService userService;

    @GetMapping("/test")
    public String info() {
        return "test";
    }

    @GetMapping("/userAuth")
    public R<UserAuthDTO> getUserAuthByUsername(String username) {
        UserAuthDTO userAuthDTO = userService.getUserAuthByAccount(username);
        return R.success(userAuthDTO);
    }
}
