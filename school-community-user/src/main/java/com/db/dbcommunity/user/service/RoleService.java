package com.db.dbcommunity.user.service;

import com.db.dbcommunity.user.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author bin
* @description 针对表【role(角色表)】的数据库操作Service
* @createDate 2023-01-31 23:24:55
*/
public interface RoleService extends IService<Role> {

    /**
     * 为角色添加相应权限
     */
    boolean saveRolePermissionById(Integer roleId, Long permissionId);

    /**
     * 将角色对应的权限信息加载到Redis
     */
    boolean refreshRolePerms();
}
