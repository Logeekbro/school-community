package com.db.dbcommunity.runner;

import com.db.dbcommunity.task.RelationTasks;
import com.db.dbcommunity.task.ScoreTasks;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableAsync
public class ScheduledRunner {

    @Resource
    private ScoreTasks scoreTasks;

    @Resource
    private RelationTasks relationTasks;

    @Async
    @Scheduled(fixedRate = 30000)
    public void syncLikeCountToES() {
        scoreTasks.syncLikeCountToES();
    }

    @Async
    @Scheduled(fixedRate = 30000)
    public void syncViewCountToES() {
        scoreTasks.syncViewCountToES();
    }

    @Async
    @Scheduled(fixedRate = 30000)
    public void syncFansCountToES() {
        relationTasks.syncFansCountToES();
    }
}
