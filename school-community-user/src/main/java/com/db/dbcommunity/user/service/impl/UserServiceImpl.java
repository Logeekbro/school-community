package com.db.dbcommunity.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.common.exception.ApiAsserts;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.user.enums.DataChangeType;
import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.db.dbcommunity.user.model.vo.UserBasicInfoVO;
import com.db.dbcommunity.user.service.UserService;
import com.db.dbcommunity.user.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.db.dbcommunity.common.MethodUtil.dataChangeCall;

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

    @Override
    public UserBasicInfoVO getUserBasicInfoById(Long userId) {
        return this.baseMapper.selectUserBasicInfoById(userId);
    }

    @Override
    public boolean updatePassword(String oldPwd, String newPwd, Long userId) {
        if(newPwd.length() < 6) {
            ApiAsserts.fail("密码长度必须大于等于6位");
        }
        User user = this.baseMapper.selectById(userId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 验证原密码
        if(encoder.matches(oldPwd, user.getPassword())){
            String encode = encoder.encode(newPwd);
            user.setPassword(encode);
            // TODO 登出账号
            String currentUserJti = UserContext.getCurrentUserJti();
            System.out.println(currentUserJti);
            return dataChangeCall(DataChangeType.UPDATE_PASSWORD.getDesc(), () -> this.baseMapper.updateById(user) > 0);
        }
        else{
            ApiAsserts.fail("原密码不正确");
        }
        return false;
    }
}




