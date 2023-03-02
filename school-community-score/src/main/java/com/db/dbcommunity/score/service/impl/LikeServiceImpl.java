package com.db.dbcommunity.score.service.impl;

import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.score.service.LikeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean changeLike(String type, Long id, Long userId, boolean isLike) {
        String statusKey;
        String scoreKey;
        switch (type) {
            case "article":
                statusKey = RedisNameSpace.ARTICLE_LIKE_STATUS_PREFIX + id;
                scoreKey = RedisNameSpace.ARTICLE_LIKE_COUNT;
                break;
            default:
                // 类型不匹配则直接返回false
                return false;
        }
        if (isLike(type, id, userId) ^ isLike) {
            // bitmap修改点赞状态
            stringRedisTemplate.opsForValue().setBit(statusKey, userId, isLike);
            // zset中修改分数(点赞数)
            stringRedisTemplate.opsForZSet().incrementScore(scoreKey, id.toString(), isLike ? 1 : -1);
            return true;
        }
        return false;
    }

    @Override
    public boolean isLike(String type, Long id, Long currentUserId) {
        String key;
        switch (type) {
            case "article":
                key = RedisNameSpace.ARTICLE_LIKE_STATUS_PREFIX + id;
                break;
            default:
                // 类型不匹配则直接返回false
                return false;
        }
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().getBit(key, currentUserId));
    }

    @Override
    public Long getLikeCount(String type, Long id) {
        String key;
        switch (type) {
            case "article":
                key = RedisNameSpace.ARTICLE_LIKE_COUNT;
                break;
            default:
                // 类型不匹配则直接返回0
                return 0L;
        }
        return stringRedisTemplate.opsForZSet().score(key, id.toString()).longValue();
    }
}
