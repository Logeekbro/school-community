package com.db.dbcommunity.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.common.exception.ApiAsserts;
import com.db.dbcommunity.common.util.MyBeanUtil;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.user.enums.DataChangeType;
import com.db.dbcommunity.user.feign.ESFeignClient;
import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.db.dbcommunity.user.model.vo.UserBasicInfoVO;
import com.db.dbcommunity.user.model.vo.UserRegisterVO;
import com.db.dbcommunity.user.service.UserService;
import com.db.dbcommunity.user.mapper.UserMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.db.dbcommunity.common.MethodUtil.dataChangeCall;

/**
* @author bin
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-01-31 23:24:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ESFeignClient esFeignClient;

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
            boolean result =
                    dataChangeCall(DataChangeType.UPDATE_PASSWORD.getDesc(), () -> this.baseMapper.updateById(user) > 0);
            if(result) {
                // 登出账号
                logout(UserContext.getCurrentUserJti(), UserContext.getCurrentUserId());
            }
            return result;
        }
        else{
            ApiAsserts.fail("原密码不正确");
        }
        return false;
    }

    @Override
    public boolean logout(String jti, Long userId) {
        return redisTemplate.opsForSet().remove(RedisNameSpace.JTI_PREFIX + userId, jti) > 0;
    }

    @Override
    public boolean login(String jti, Long userId) {
        if(redisTemplate.opsForSet().size(RedisNameSpace.JTI_PREFIX + userId) >= UserConstant.MAX_CLIENT_COUNT) {
            // 如果登录的客户端数量超出限制，则移除第一个客户端的jti
            redisTemplate.opsForSet().pop(RedisNameSpace.JTI_PREFIX + userId);
        }
        return redisTemplate.opsForSet().add(RedisNameSpace.JTI_PREFIX + userId, jti) > 0;
    }

    @Override
    public boolean ban(Long userId) {
        // 删除jti集合
        boolean redisDelete = Boolean.TRUE.equals(redisTemplate.delete(RedisNameSpace.JTI_PREFIX + userId));
        // 改变status字段
        User user = new User();
        user.setUserId(userId);
        user.setStatus(1);
        boolean mysqlDelete = dataChangeCall(DataChangeType.BAN_USER.getDesc(), () -> this.baseMapper.updateById(user) > 0);
        return redisDelete && mysqlDelete;
    }

    @Override
    public boolean register(UserRegisterVO vo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, vo.getUsername()).or().eq(User::getEmail, vo.getEmail());
        if(this.baseMapper.exists(queryWrapper)) ApiAsserts.fail("用户名或邮箱已存在");
        User user = MyBeanUtil.copyProps(vo, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        boolean result = dataChangeCall(DataChangeType.USER_REGISTER.getDesc(), () -> this.baseMapper.insert(user) > 0);
        if(result) {
            User[] users = new User[1];
            users[0] = user;
            R<Void> resp = esFeignClient.indexUser(users);
            if(!resp.getCode().equals(200)) {
                ApiAsserts.fail(resp.getMessage());
            }
        }
        return result;
    }
}




