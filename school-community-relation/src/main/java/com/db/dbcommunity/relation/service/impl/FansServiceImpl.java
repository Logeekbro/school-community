package com.db.dbcommunity.relation.service.impl;

import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.relation.service.FansService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FansServiceImpl implements FansService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public MyPage<String> getFansList(Long currentUserId, Long current, Short size) {
        String key = RedisNameSpace.FANS_PREFIX + currentUserId;
        MyPage<String> myPage = new MyPage<>(current, size, getFansCountByUserId(currentUserId));
        myPage.setRecords(stringRedisTemplate.opsForZSet().range(key, myPage.offset(), myPage.offset() + size - 1));
        return myPage;
    }

    @Override
    public Long getFansCountByUserId(Long userId) {
        return stringRedisTemplate.opsForZSet().size(RedisNameSpace.FANS_PREFIX + userId);
    }
}
