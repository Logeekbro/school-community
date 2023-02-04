package com.db.dbcommunity.user.service;

import com.db.dbcommunity.user.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author bin
* @description 针对表【role(角色表)】的数据库操作Service
* @createDate 2023-01-31 23:24:55
*/
public interface RoleService extends IService<Role> {

    boolean saveRolePermissionById(String roleId, Integer permissionId);
}
