package com.db.dbcommunity.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.db.dbcommunity.user.service.UserService;
import com.db.dbcommunity.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author bin
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-01-31 23:24:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public UserAuthDTO getUserAuthByAccount(String account) {
        return this.baseMapper.selectUserAuthByAccount(account);
    }
}




