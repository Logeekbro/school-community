package com.db.dbcommunity.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.user.model.entity.Role;
import com.db.dbcommunity.user.service.RoleService;
import com.db.dbcommunity.user.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author bin
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2023-01-31 23:24:55
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

}




