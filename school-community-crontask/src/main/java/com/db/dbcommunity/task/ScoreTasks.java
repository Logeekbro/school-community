package com.db.dbcommunity.task;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.feign.ESFeignClient;
import com.db.dbcommunity.feign.ScoreFeignClient;
import com.db.dbcommunity.feign.VisitFeignClient;
import com.db.dbcommunity.thread.MyThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScoreTasks {

    private final int limit = 10000;

    private final AtomicInteger failCount = new AtomicInteger(0);

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource()
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ScoreFeignClient scoreFeignClient;

    @Resource
    private VisitFeignClient visitFeignClient;

    @Resource
    private ESFeignClient esFeignClient;

    private boolean isToManyFail() {
        if (failCount.get() > 10) {
            //TODO 多次失败，发出警告
            logger.error("失败次数过多！");
            return true;
        }
        return false;
    }

    public void syncLikeCountToES() {
        if(isToManyFail()) return;
        Set<String> articleIds = stringRedisTemplate.opsForSet().members(RedisNameSpace.LIKE_COUNT_BE_UPDATED_ARTICLE_IDS);
        if(articleIds != null && articleIds.size() > 0) {
            int size = articleIds.size();
            CountDownLatch latch = new CountDownLatch(size);
            List<Map<String, Object>> articles;
            if(size <= limit) articles = Collections.synchronizedList(new ArrayList<>());
            else articles = new CopyOnWriteArrayList<>();
            for (String articleId : articleIds) {
                MyThreadPoolExecutor.run(() -> {
                    R<SingleKeyVO> resp = scoreFeignClient.getArticleLikeCount(articleId);
                    if(!resp.getCode().equals(200)) {
                        latch.countDown();
                        return;
                    }
                    Map<String, Object> article = new HashMap<>();
                    Object likeCount = resp.getData().getValue();
                    article.put("likeCount", likeCount);
                    article.put("articleId", articleId);
                    articles.add(article);
                    stringRedisTemplate.opsForSet().remove(RedisNameSpace.LIKE_COUNT_BE_UPDATED_ARTICLE_IDS, articleId);
                    latch.countDown();
                });
            }
            try {
                boolean isSuccess = latch.await(3, TimeUnit.MINUTES);
                if(!isSuccess) {
                    logger.error("生成文章点赞数信息失败");
                    failCount.getAndIncrement();
                    return;
                }
                R<Void> resp = esFeignClient.updateArticleIndex(articles);
                if(!resp.getCode().equals(200)) {
                    logger.error("更新点赞数到ES失败->{}", resp.getMessage());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void syncViewCountToES() {
        if(isToManyFail()) return;
        Set<String> articleIds = stringRedisTemplate.opsForSet().members(RedisNameSpace.VIEW_COUNT_BE_UPDATED_ARTICLE_IDS);
        if(articleIds != null && articleIds.size() > 0) {
            int size = articleIds.size();
            CountDownLatch latch = new CountDownLatch(size);
            List<Map<String, Object>> articles;
            if(size <= limit) articles = Collections.synchronizedList(new ArrayList<>());
            else articles = new CopyOnWriteArrayList<>();
            for (String articleId : articleIds) {
                MyThreadPoolExecutor.run(() -> {
                    R<SingleKeyVO> resp = visitFeignClient.getVisitCountByArticleId(articleId);
                    if(!resp.getCode().equals(200)) {
                        latch.countDown();
                        return;
                    }
                    Map<String, Object> article = new HashMap<>();
                    Object viewCount = resp.getData().getValue();
                    article.put("viewCount", viewCount);
                    article.put("articleId", articleId);
                    articles.add(article);
                    stringRedisTemplate.opsForSet().remove(RedisNameSpace.VIEW_COUNT_BE_UPDATED_ARTICLE_IDS, articleId);
                    latch.countDown();
                });
            }
            try {
                boolean isSuccess = latch.await(3, TimeUnit.MINUTES);
                if(!isSuccess) {
                    logger.error("生成文章浏览量信息失败");
                    failCount.getAndIncrement();
                    return;
                }
                R<Void> resp = esFeignClient.updateArticleIndex(articles);
                if(!resp.getCode().equals(200)) {
                    logger.error("更新文章浏览量到ES失败->{}", resp.getMessage());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
