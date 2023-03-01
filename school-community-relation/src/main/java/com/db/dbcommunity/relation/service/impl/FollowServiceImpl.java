package com.db.dbcommunity.relation.service.impl;

import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.relation.service.FollowService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;


    @Override
    public boolean changeFollow(Long currentUserId, Long beFollowUserId, boolean isFollow) {
        if(isFollow) {
            long now = System.currentTimeMillis();
            redisTemplate.opsForZSet()
                    .add(RedisNameSpace.FOLLOW_PREFIX + currentUserId, beFollowUserId, now);
            redisTemplate.opsForZSet()
                    .add(RedisNameSpace.FANS_PREFIX + beFollowUserId, currentUserId, now);
        } else {
            redisTemplate.opsForZSet()
                    .remove(RedisNameSpace.FOLLOW_PREFIX + currentUserId, beFollowUserId);
            redisTemplate.opsForZSet()
                    .remove(RedisNameSpace.FANS_PREFIX + beFollowUserId, currentUserId);
        }
        return true;
    }

    @Override
    public boolean isFollow(Long currentUserId, Long beFollowUserId) {
        return redisTemplate.opsForZSet().rank(RedisNameSpace.FOLLOW_PREFIX + currentUserId, beFollowUserId) != null;
    }

    @Override
    public MyPage<Long> getFollowList(Long currentUserId, Long current, Short size) {
        String key = RedisNameSpace.FOLLOW_PREFIX + currentUserId;
        Long total = redisTemplate.opsForZSet().size(key);
        MyPage<Long> myPage = new MyPage<>(current, size, total);
        myPage.setRecords(redisTemplate.opsForZSet().range(key, myPage.offset(), myPage.offset() + size - 1));
        return myPage;
    }

}
