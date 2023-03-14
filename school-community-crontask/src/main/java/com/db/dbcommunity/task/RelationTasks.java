package com.db.dbcommunity.task;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.RedisNameSpace;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.feign.ESFeignClient;
import com.db.dbcommunity.feign.RelationFeignClient;
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
public class RelationTasks {

    private final int limit = 10000;

    private final AtomicInteger failCount = new AtomicInteger(0);

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RelationFeignClient relationFeignClient;

    @Resource
    private ESFeignClient esFeignClient;

    private boolean isToManyFail() {
        if (failCount.get() > 10) {
            //TODO 多次失败，发出警告
            return true;
        }
        return false;
    }

    public void syncFansCountToES() {
        if(isToManyFail()) return;
        Set<String> userIds = stringRedisTemplate.opsForSet().members(RedisNameSpace.FANS_COUNT_BE_UPDATED_USER_IDS);
        if(userIds != null && userIds.size() > 0) {
            int size = userIds.size();
            CountDownLatch latch = new CountDownLatch(size);
            List<Map<String, Object>> users;
            if(size <= limit) users = Collections.synchronizedList(new ArrayList<>());
            else users = new CopyOnWriteArrayList<>();
            for (String userId : userIds) {
                MyThreadPoolExecutor.run(() -> {
                    R<SingleKeyVO> resp = relationFeignClient.getFansCountByUserId(userId);
                    if(!resp.getCode().equals(200)) {
                        latch.countDown();
                        return;
                    }
                    Map<String, Object> user = new HashMap<>();
                    Object fansCount = resp.getData().getValue();
                    user.put("fansCount", fansCount);
                    user.put("userId", userId);
                    users.add(user);
                    stringRedisTemplate.opsForSet().remove(RedisNameSpace.FANS_COUNT_BE_UPDATED_USER_IDS, userId);
                    latch.countDown();
                });
            }
            try {
                boolean isSuccess = latch.await(3, TimeUnit.MINUTES);
                if(!isSuccess) {
                    logger.error("生成用户粉丝数信息失败");
                    failCount.getAndIncrement();
                    return;
                }
                R<Void> resp = esFeignClient.updateUserIndex(users);
                if(!resp.getCode().equals(200)) {
                    logger.error("更新用户粉丝数到ES失败->{}", resp.getMessage());
                }
            } catch (InterruptedException e) {
                logger.warn("强制退出...");
            }
        }
    }
}
