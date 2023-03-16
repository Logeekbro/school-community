package com.db.dbcommunity.user.mapper;

import com.db.dbcommunity.user.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author bin
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2023-01-31 23:24:55
* @Entity com.db.dbcommunity.model.entity.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRolePerms();
}




