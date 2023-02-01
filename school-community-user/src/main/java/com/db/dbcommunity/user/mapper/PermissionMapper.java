package com.db.dbcommunity.user.mapper;

import com.db.dbcommunity.user.model.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author bin
* @description 针对表【permission(权限表)】的数据库操作Mapper
* @createDate 2023-01-31 23:24:55
* @Entity com.db.dbcommunity.model.entity.Permission
*/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectPermRoles();
}




