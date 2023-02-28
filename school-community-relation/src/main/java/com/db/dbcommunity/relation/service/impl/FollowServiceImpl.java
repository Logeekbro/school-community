package com.db.dbcommunity.relation.service.impl;

import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.relation.service.FollowService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    private RedisTemplate<String, Boolean> redisTemplate;


    @Override
    public boolean changeFollow(Long currentUserId, Long beFollowUserId, boolean isFollow) {
        redisTemplate.opsForValue().setBit(RedisNameSpace.FOLLOW_PREFIX + currentUserId, beFollowUserId, isFollow);
        return true;
    }

    @Override
    public boolean isFollow(Long currentUserId, Long beFollowUserId) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForValue().getBit(RedisNameSpace.FOLLOW_PREFIX + currentUserId, beFollowUserId));
    }

}
