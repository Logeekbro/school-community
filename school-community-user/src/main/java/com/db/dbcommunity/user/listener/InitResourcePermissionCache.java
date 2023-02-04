package com.db.dbcommunity.user.listener;


import com.db.dbcommunity.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class InitResourcePermissionCache implements CommandLineRunner {

    @Resource
    private PermissionService permissionService;

    @Override
    public void run(String... args) {
        if(permissionService.refreshPermRolesRules()) {
            log.info("加载角色权限成功");
        };
    }
}
