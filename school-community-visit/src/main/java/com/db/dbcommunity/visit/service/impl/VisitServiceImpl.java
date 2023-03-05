package com.db.dbcommunity.visit.service.impl;

import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.visit.service.VisitService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean handleVisit(Long currentUserId, Long articleId) {
        // 检查该用户是否已经浏览过该文章
        boolean isVisit = Boolean.TRUE.equals(stringRedisTemplate.opsForValue().getBit(RedisNameSpace.ARTICLE_VISIT_STATUS + articleId, currentUserId));
        if(!isVisit) {
            // 没浏览过则:
            //0. 开启事务
            stringRedisTemplate.setEnableTransactionSupport(true);
            stringRedisTemplate.multi();
            //1. 改变浏览状态
            stringRedisTemplate.opsForValue().setBit(RedisNameSpace.ARTICLE_VISIT_STATUS + articleId, currentUserId, true);
            //2. 增加浏览量
            stringRedisTemplate.opsForZSet().incrementScore(RedisNameSpace.ARTICLE_VISIT_COUNT, articleId.toString(), 1);
            //3. 提交事务
            stringRedisTemplate.exec();
        }
        return true;
    }
}
