package com.db.dbcommunity.user.controller;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.user.model.entity.Permission;
import com.db.dbcommunity.user.model.vo.PermissionCreateVO;
import com.db.dbcommunity.user.model.vo.PermissionUpdateVO;
import com.db.dbcommunity.user.service.PermissionService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/permission")
public class UserPermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 新增权限
     * @return 是否新增成功
     */
    @PostMapping(value = "/", name = "新增权限_f")
    public R<Boolean> addPermission(@RequestBody PermissionCreateVO permissionCreateVO) {
        return permissionService.savePermission(permissionCreateVO) ? R.success() : R.failed();
    }

    /**
     * 根据id删除权限
     * @param id 权限id
     * @return 是否删除成功
     */
    @DeleteMapping(value = "/id/{id}", name = "根据id删除权限_f")
    public R<Void> deletePermissionById(@PathVariable Long id) {
        return permissionService.deletePermissionById(id) ? R.success() : R.failed();
    }

    /**
     * 修改权限
     */
    @PutMapping(value = "/", name = "修改权限_f")
    public R<Void> updatePermission(@RequestBody PermissionUpdateVO vo) {
        return permissionService.updatePermission(vo) ? R.success() : R.failed();

    }

    /**
     * 获取所有权限列表
     */
    @GetMapping(value = "/all", name = "获取所有权限列表_f")
    public R<List<Permission>> getAllPermission() {
        return R.success(permissionService.list());
    }

}
