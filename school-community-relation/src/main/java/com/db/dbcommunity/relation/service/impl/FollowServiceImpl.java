package com.db.dbcommunity.relation.service.impl;

import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.relation.service.FollowService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    private StringRedisTemplate redisTemplate;


    @Override
    public boolean changeFollow(Long currentUserId, Long beFollowUserId, boolean isFollow) {
        if(isFollow) {
            long now = System.currentTimeMillis();
            redisTemplate.opsForZSet()
                    .add(RedisNameSpace.FOLLOW_PREFIX + currentUserId, beFollowUserId.toString(), now);
            redisTemplate.opsForZSet()
                    .add(RedisNameSpace.FANS_PREFIX + beFollowUserId, currentUserId.toString(), now);
        } else {
            redisTemplate.opsForZSet()
                    .remove(RedisNameSpace.FOLLOW_PREFIX + currentUserId, beFollowUserId);
            redisTemplate.opsForZSet()
                    .remove(RedisNameSpace.FANS_PREFIX + beFollowUserId, currentUserId);
        }
        // 将当前被关注者id存入粉丝数量有更新的集合中
        redisTemplate.opsForSet().add(RedisNameSpace.FANS_COUNT_BE_UPDATED_USER_IDS, beFollowUserId.toString());
        return true;
    }

    @Override
    public boolean isFollow(Long currentUserId, Long beFollowUserId) {
        return redisTemplate.opsForZSet().rank(RedisNameSpace.FOLLOW_PREFIX + currentUserId, beFollowUserId) != null;
    }

    @Override
    public MyPage<String> getFollowList(Long currentUserId, Long current, Short size) {
        String key = RedisNameSpace.FOLLOW_PREFIX + currentUserId;
        Long total = redisTemplate.opsForZSet().size(key);
        MyPage<String> myPage = new MyPage<>(current, size, total);
        myPage.setRecords(redisTemplate.opsForZSet().range(key, myPage.offset(), myPage.offset() + size - 1));
        return myPage;
    }

}
