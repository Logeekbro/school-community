package com.db.dbcommunity.user.controller;

import com.db.dbcommunity.common.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegisterController {

    @PostMapping("/register")
    public R register() {
        return null;
    }
}
