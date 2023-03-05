package com.db.dbcommunity.message.consumer;

import com.db.dbcommunity.message.document.SystemMessage;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SystemMessageConsumer {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 发送系统消息
     */
    @RabbitListener(queuesToDeclare = @Queue("system.message"))
    public void sendSystemMessage(SystemMessage systemMessage) {
        mongoTemplate.insert(systemMessage);
    }
}
