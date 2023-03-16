package com.db.dbcommunity.user;

import com.db.dbcommunity.user.mapper.RoleMapper;
import com.db.dbcommunity.user.model.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class SchoolCommunityUserApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
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
