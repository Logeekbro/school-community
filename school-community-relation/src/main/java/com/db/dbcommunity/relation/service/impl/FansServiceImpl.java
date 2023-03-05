package com.db.dbcommunity.relation.service.impl;

import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.relation.service.FansService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FansServiceImpl implements FansService {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Override
    public MyPage<Long> getFansList(Long currentUserId, Long current, Short size) {
        String key = RedisNameSpace.FANS_PREFIX + currentUserId;
        Long total = redisTemplate.opsForZSet().size(key);
        MyPage<Long> myPage = new MyPage<>(current, size, total);
        myPage.setRecords(redisTemplate.opsForZSet().range(key, myPage.offset(), myPage.offset() + size - 1));
        return myPage;
    }
}