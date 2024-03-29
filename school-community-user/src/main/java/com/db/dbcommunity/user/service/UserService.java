package com.db.dbcommunity.user.service;

import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.dto.UserDetailInfoDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.dbcommunity.user.model.vo.UserDetailInfoVO;
import com.db.dbcommunity.user.model.vo.UserBasicInfoVO;
import com.db.dbcommunity.user.model.vo.UserRegisterVO;

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

    /**
     * 根据用户id获取用户的基础信息
     * @param userId 用户id
     * @return 用户的基础信息
     */
    UserBasicInfoVO getUserBasicInfoById(Long userId);

    /**
     * 修改用户密码
     */
    boolean updatePassword(String oldPwd, String newPwd, Long currentUserId);

    /**
     * 用户退出登录
     */
    boolean logout(String jti, Long userId);

    /**
     * 将jti注册到系统中
     */
    boolean login(String jti, Long userId);

    /**
     * 封禁用户
     */
    boolean ban(Long userId);

    /**
     * 账号注册
     */
    boolean register(UserRegisterVO vo);

    /**
     * 根据用户id获取用户头像
     */
    String getAvatarByUserId(Long userId);

    /**
     * 根据用户id获取用户昵称
     */
    String getNickNameByUserId(Long userId);

    /**
     * 修改用户基本信息
     */
    boolean updateDetailInfo(UserDetailInfoDTO vo);
}
