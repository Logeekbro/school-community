package com.db.dbcommunity.visit.service.impl;

import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.visit.service.VisitService;
import org.springframework.dao.DataAccessException;
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
            stringRedisTemplate.opsForValue().setBit(RedisNameSpace.ARTICLE_VISIT_STATUS + articleId, currentUserId, true);
            stringRedisTemplate.opsForZSet().incrementScore(RedisNameSpace.ARTICLE_VISIT_COUNT, articleId.toString(), 1);
            stringRedisTemplate.opsForSet().add(RedisNameSpace.VIEW_COUNT_BE_UPDATED_ARTICLE_IDS, articleId.toString());
//            stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
//                public List<Object> execute(RedisOperations operations) throws DataAccessException {
//                    //0. 开启事务
//                    operations.multi();
//                    //1. 改变浏览状态
//                    operations.opsForValue().setBit(RedisNameSpace.ARTICLE_VISIT_STATUS + articleId, currentUserId, true);
//                    //2. 增加浏览量
//                    operations.opsForZSet().incrementScore(RedisNameSpace.ARTICLE_VISIT_COUNT, articleId.toString(), 1);
//                    //3. 将当前文章id加入到浏览量有更新的集合中
//                    operations.opsForSet().add(RedisNameSpace.VIEW_COUNT_BE_UPDATED_ARTICLE_IDS, articleId.toString());
//                    //4. 提交事务
//                    return operations.exec();
//                }
//            });

        }
        return true;
    }

    @Override
    public Long getVisitCountByArticleId(Long articleId) {
        Double score = stringRedisTemplate.opsForZSet().score(RedisNameSpace.ARTICLE_VISIT_COUNT, articleId.toString());
        return score != null ? score.longValue() : 0L;
    }
}
