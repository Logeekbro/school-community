package com.db.dbcommunity.user.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.user.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(UserConstant.URI_PREFIX + "/role")
public class UserRoleController {

    @Resource
    private RoleService roleService;

    /**
     * 为角色添加相应的权限
     * @param permissionId 权限id
     * @param roleId 角色id
     * @return 是否添加成功
     */
    @PostMapping("/id/{roleId}")
    public R<Boolean> addRolePermissionById(@PathVariable String roleId, @RequestParam Integer permissionId) {
        return roleService.saveRolePermissionById(roleId, permissionId) ? R.success() : R.failed();
    }
}
