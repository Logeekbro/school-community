package com.db.dbcommunity.user.service;

import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author bin
* @description 针对表【user】的数据库操作Service
* @createDate 2023-01-31 23:24:55
*/
public interface UserService extends IService<User> {

    /**
     * 根据用户的用户名、手机号、邮箱等信息获取用户的认证信息
     * @param account 可以是：用户名、手机号、邮箱
     * @return 用户的认证信息
     */
    UserAuthDTO getUserAuthByAccount(String account);
}
