package com.db.dbcommunity.user;

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

}
