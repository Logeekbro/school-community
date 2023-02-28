package com.db.dbcommunity.relation.service.impl;

import com.db.dbcommunity.relation.service.FollowService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    private RedisTemplate<Long, Boolean> redisTemplate;


    @Override
    public boolean addFollow(Long currentUserId, Long beFollowUserId) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setBit(currentUserId, beFollowUserId, true));
    }

    @Override
    public boolean deleteFollow(Long currentUserId, Long beFollowUserId) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setBit(currentUserId, beFollowUserId, false));
    }
}
