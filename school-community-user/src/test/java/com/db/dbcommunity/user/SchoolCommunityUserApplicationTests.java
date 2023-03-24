package com.db.dbcommunity.user;

import com.db.dbcommunity.user.controller.UserPermissionController;
import com.db.dbcommunity.user.controller.UserRoleController;
import com.db.dbcommunity.user.mapper.RoleMapper;
import com.db.dbcommunity.user.model.entity.Role;
import com.db.dbcommunity.user.model.vo.PermissionCreateVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class SchoolCommunityUserApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ApplicationContext applicationContext;

    @Test
    void generatePermission() {
        AbstractHandlerMethodMapping<RequestMappingInfo> objHandlerMethodMapping = (AbstractHandlerMethodMapping<RequestMappingInfo>)applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> mapRet = objHandlerMethodMapping.getHandlerMethods();
        for(RequestMappingInfo r : mapRet.keySet()) {
            if(r.getName() == null) continue;
            boolean isUserDefault;
            String name = r.getName();
            String[] split = name.split("_");
            if(split.length == 2) {
                isUserDefault = split[1].equals("true");
            } else {
                System.out.println("名称格式有误--->" + name);
                continue;
            }
            Set<RequestMethod> methods = r.getMethodsCondition().getMethods();
            for(RequestMethod method : methods) {
                PathPatternsRequestCondition patternsCondition = r.getPathPatternsCondition();
                if(patternsCondition == null) continue;
                for(PathPattern path : patternsCondition.getPatterns()) {
                    String ps = path.getPatternString().replaceAll("\\{.*?}", "*");
                    String urlPerm = method + ":" + ps;
                    PermissionCreateVO permissionCreateVO = new PermissionCreateVO();
                    permissionCreateVO.setIsUserDefault(isUserDefault);
                    permissionCreateVO.setName(name.substring(0, name.indexOf('_')));
                    permissionCreateVO.setUrlPerm(urlPerm);
                    System.out.println(permissionCreateVO);
                }
            }
        }
    }

    @Test
    void testRedisCache() {
        redisTemplate.opsForValue().set("qwe", "");
        String qwe = (String)redisTemplate.opsForValue().get("qwe");
        System.out.println("qwe: " + qwe);
        redisTemplate.delete("qwe");
    }

    @Test
    void testRedisSet() {
//        Boolean member = redisTemplate.opsForSet().isMember("qwjughe", "qwe");
//        System.out.println(member);
        Long gwijfqwd = redisTemplate.opsForSet().size("gwijfqwd");
        System.out.println(gwijfqwd);
    }

    @Resource
    private RoleMapper roleMapper;

    @Test
    void testNewRolePerms() {
        for (Role selectRolePerm : roleMapper.selectRolePerms()) {
            System.out.println(selectRolePerm);
        }

    }

}
