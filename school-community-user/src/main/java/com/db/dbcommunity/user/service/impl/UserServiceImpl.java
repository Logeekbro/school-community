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
import com.db.dbcommunity.user.model.dto.UserDetailInfoDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.db.dbcommunity.user.model.vo.UserDetailInfoVO;
import com.db.dbcommunity.user.model.vo.UserBasicInfoVO;
import com.db.dbcommunity.user.model.vo.UserRegisterVO;
import com.db.dbcommunity.user.service.RoleService;
import com.db.dbcommunity.user.service.UserService;
import com.db.dbcommunity.user.mapper.UserMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ESFeignClient esFeignClient;

    @Resource
    private RoleService roleService;

    @Override
    public UserAuthDTO getUserAuthByAccount(String account) {
        return this.baseMapper.selectUserAuthByAccount(account);
    }

    @Override
    public UserBasicInfoVO getUserBasicInfoById(Long userId) {
        return this.baseMapper.selectUserBasicInfoById(userId);
    }

    /**
     * 清除该用户所有已登录设备
     */
    private boolean clearLoginStatus(Long userId) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(RedisNameSpace.JTI_PREFIX + userId));
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
                // 清除已登陆账号
                clearLoginStatus(userId);
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
        return stringRedisTemplate.opsForSet().remove(RedisNameSpace.JTI_PREFIX + userId, jti) > 0;
    }

    @Override
    public boolean login(String jti, Long userId) {
        if(stringRedisTemplate.opsForSet().size(RedisNameSpace.JTI_PREFIX + userId) >= UserConstant.MAX_CLIENT_COUNT) {
            // 如果登录的客户端数量超出限制，则移除第一个客户端的jti
            stringRedisTemplate.opsForSet().pop(RedisNameSpace.JTI_PREFIX + userId);
        }
        return stringRedisTemplate.opsForSet().add(RedisNameSpace.JTI_PREFIX + userId, jti) > 0;
    }

    @Override
    public boolean ban(Long userId) {
        // 删除jti集合
        boolean redisDelete =  clearLoginStatus(userId);
        // 改变status字段
        User user = new User();
        user.setUserId(userId);
        user.setStatus(1);
        boolean mysqlDelete = dataChangeCall(DataChangeType.BAN_USER.getDesc(), () -> this.baseMapper.updateById(user) > 0);
        return redisDelete && mysqlDelete;
    }

    @Transactional
    @Override
    public boolean register(UserRegisterVO vo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, vo.getUsername()).or().eq(User::getEmail, vo.getEmail());
        if(this.baseMapper.exists(queryWrapper)) ApiAsserts.fail("用户名或邮箱已存在");
        User user = MyBeanUtil.copyProps(vo, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        if(vo.getNickName() == null) user.setNickName(vo.getUsername());
        boolean result = dataChangeCall(DataChangeType.USER_REGISTER.getDesc(), () -> this.baseMapper.insert(user) > 0);
        if(result) {
            // 索引用户至ES
            User[] users = new User[1];
            users[0] = user;
            R<Void> resp = esFeignClient.indexUser(users);
            if(!resp.getCode().equals(200)) {
                ApiAsserts.fail(resp.getMessage());
            }
            // 为该用户添加默认的用户角色
            roleService.addUserRoleByUserId(user.getUserId(), 10);
        }
        return result;
    }

    @Override
    public String getAvatarByUserId(Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getAvatar);
        queryWrapper.eq(User::getUserId, userId);
        User user = this.baseMapper.selectOne(queryWrapper);
        return user != null ? user.getAvatar() : null;

    }

    @Override
    public String getNickNameByUserId(Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getNickName);
        queryWrapper.eq(User::getUserId, userId);
        User user = this.baseMapper.selectOne(queryWrapper);
        return user != null ? user.getNickName() : null;
    }

    @Override
    public boolean updateDetailInfo(UserDetailInfoDTO vo) {
        return dataChangeCall(DataChangeType.UPDATE_BASIC_INFO.getDesc(), () -> this.baseMapper.updateById(MyBeanUtil.copyProps(vo, User.class)) > 0);
    }
}




