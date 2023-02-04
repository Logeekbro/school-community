package com.db.dbcommunity.user.controller;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.user.model.entity.Permission;
import com.db.dbcommunity.user.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/permission")
public class UserPermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 新增权限
     * @param permission 需要新增的权限信息
     * @return 是否新增成功
     */
    @PostMapping("/")
    public R<Boolean> addPermission(Permission permission) {
        if(permissionService.save(permission)) {
            permissionService.refreshPermRolesRules();
            return R.success();
        }
        return R.failed();
    }

    /**
     * 根据id删除权限
     * @param id 权限id
     * @return 是否删除成功
     */
    @DeleteMapping("/id/{id}")
    public R<Boolean> deletePermissionById(@PathVariable String id) {
        if(permissionService.removeById(id)) {
            permissionService.refreshPermRolesRules();
            return R.success();
        }
        return R.failed();
    }
}
