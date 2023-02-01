package com.db.dbcommunity.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.common.constant.GlobalConstant;
import com.db.dbcommunity.user.model.entity.Permission;
import com.db.dbcommunity.user.service.PermissionService;
import com.db.dbcommunity.user.mapper.PermissionMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author bin
* @description 针对表【permission(权限表)】的数据库操作Service实现
* @createDate 2023-01-31 23:24:55
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean refreshPermRolesRules() {
        redisTemplate.delete(Collections.singletonList(GlobalConstant.URL_PERM_ROLES_KEY));
        List<Permission> permissions = this.baseMapper.selectPermRoles();
        if (CollectionUtil.isNotEmpty(permissions)) {
            List<Permission> urlPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getUrlPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(urlPermList)) {
                Map<String, List<String>> urlPermRoles = new HashMap<>();
                urlPermList.forEach(item -> {
                    String perm = item.getUrlPerm();
                    List<String> roles = item.getRoles();
                    urlPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(GlobalConstant.URL_PERM_ROLES_KEY, urlPermRoles);
            }
        }
        return true;
    }
}




