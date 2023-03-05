package com.db.dbcommunity.message.provider;

import com.db.dbcommunity.message.document.LikeNotion;
import com.db.dbcommunity.message.document.ReviewResultNotion;
import com.db.dbcommunity.message.document.SystemMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SystemMessageProvider {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendSysMessage(SystemMessage systemMessage) {
        if(systemMessage instanceof LikeNotion) {
            ((LikeNotion) systemMessage).setLikeUserId(1L);
        } else if(systemMessage instanceof ReviewResultNotion) {

        }
        System.out.println(systemMessage);
//        rabbitTemplate.convertAndSend("system.message", systemMessage);
    }
}
