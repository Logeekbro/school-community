package com.db.dbcommunity.user.service;

import com.db.dbcommunity.user.model.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author bin
* @description 针对表【permission(权限表)】的数据库操作Service
* @createDate 2023-01-31 23:24:55
*/
public interface PermissionService extends IService<Permission> {

    /**
     * 将所有 权限及拥有该权限的角色列表 对象 加载到redis中
     * @return 是否操作成功
     */
    boolean refreshPermRolesRules();
}
