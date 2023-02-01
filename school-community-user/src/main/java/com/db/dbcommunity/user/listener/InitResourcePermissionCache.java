package com.db.dbcommunity.user.listener;


import com.db.dbcommunity.user.service.PermissionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class InitResourcePermissionCache implements CommandLineRunner {

    private PermissionService permissionService;

    @Override
    public void run(String... args) {
        if(permissionService.refreshPermRolesRules()) {
            log.info("加载角色权限成功");
        };
    }
}
