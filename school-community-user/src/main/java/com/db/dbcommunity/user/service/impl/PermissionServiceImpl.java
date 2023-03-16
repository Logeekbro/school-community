package com.db.dbcommunity.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.common.constant.GlobalConstant;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.user.model.entity.Permission;
import com.db.dbcommunity.user.model.vo.PermissionCreateVO;
import com.db.dbcommunity.user.model.vo.PermissionUpdateVO;
import com.db.dbcommunity.user.service.PermissionService;
import com.db.dbcommunity.user.mapper.PermissionMapper;
import com.db.dbcommunity.user.service.RoleService;
import com.db.dbcommunity.user.thread.MyThreadPoolExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RoleService roleService;

    @Deprecated
    @Override
    public boolean refreshPermRolesRules() {
        stringRedisTemplate.delete(Collections.singletonList(GlobalConstant.ROLE_URL_PERMS_KEY));
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
                stringRedisTemplate.opsForHash().putAll(GlobalConstant.ROLE_URL_PERMS_KEY, urlPermRoles);
            }
        }
        return true;
    }

    @Override
    public boolean savePermission(PermissionCreateVO permissionVo) {
        Permission permission =
                new Permission(null, permissionVo.getName(), permissionVo.getGroupId(), permissionVo.getUrlPerm());
        if(this.CUD(permission, 'c')) {
            if(permissionVo.getIsUserDefault()) MyThreadPoolExecutor.run(() -> roleService.saveRolePermissionById(UserConstant.DEFAULT_USER_ROLE_ID, permission.getId()));
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePermission(PermissionUpdateVO updateVO) {
        Permission permission = new Permission(updateVO.getId(), updateVO.getName(), updateVO.getGroupId(), updateVO.getUrlPerm());
        return CUD(permission, 'u');
    }

    @Override
    public boolean deletePermissionById(Long id) {
        Permission permission = new Permission();
        permission.setId(id);
        return CUD(permission, 'd');
    }

    /**
     * 增删改的统一操作接口，方便统一添加一些刷新缓存之类的操作
     * @param ops 操作：c-增 u-改 d-删
     * @return 是否操作成功
     */
    private boolean CUD(Permission permission, char ops) {
        int result = 0;
        // C: 新增，U：更新，D：删除
        switch (ops) {
            case 'c':
                result = this.baseMapper.insert(permission);
                break;
            case 'u':
                result = this.baseMapper.updateById(permission);
                break;
            case 'd':
                result = this.baseMapper.deleteById(permission);
        }
        if(result > 0) {
            // 刷新缓存
            MyThreadPoolExecutor.run(() -> roleService.refreshRolePerms());
            return true;
        }
        return false;
    }
}




