package com.db.dbcommunity.user.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.user.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class UserRoleController {

    @Resource
    private RoleService roleService;

    /**
     * 为角色添加相应的权限
     * @param permissionId 权限id
     * @param roleId 角色id
     * @return 是否添加成功
     */
    @PostMapping(value = "/id/{roleId}", name = "为角色添加相应的权限_f")
    public R<Void> addRolePermissionById(@PathVariable Integer roleId, @RequestParam Long permissionId) {
        return roleService.saveRolePermissionById(roleId, permissionId) ? R.success() : R.failed();
    }

    /**
     * 为用户添加角色
     */
    @PostMapping(value = "/user/{userId}", name = "为用户添加角色_f")
    public R<Void> addUserRoleByUserId(@RequestParam Integer roleId, @PathVariable Long userId) {
        return roleService.addUserRoleByUserId(userId, roleId) ? R.success() : R.failed();
    }

    /**
     * 获取用户的角色id列表
     */
    @GetMapping(value = "/list/id", name = "获取用户的角色id列表_true")
    public R<List<?>> getUserRoleIdList(@RequestParam Long userId) {
        return R.success(roleService.getUserRoleIdList(userId));
    }
}
