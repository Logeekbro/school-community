package com.db.dbcommunity.user.listener;


import com.db.dbcommunity.user.service.PermissionService;
import com.db.dbcommunity.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class InitResourcePermissionCache implements CommandLineRunner {

    @Resource
    private RoleService roleService;

    @Value("${server.port}")
    private Integer port;

    @Override
    public void run(String... args) {
        // 此处判断端口的目的是为了在多实例情况下只运行一次
        if(port.equals(1000) && roleService.refreshRolePerms()) {
            log.info("加载角色权限成功");
        }
    }
}
