package com.db.dbcommunity.message;


import com.db.dbcommunity.message.document.LikeNotion;
import com.db.dbcommunity.message.document.SystemMessage;
import com.db.dbcommunity.message.provider.SystemMessageProvider;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class AppTest 
{

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SystemMessageProvider systemMessageProvider;

    @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println(rabbitTemplate);
    }

    @Test
    void testInstance() {
        SystemMessage systemMessage = new LikeNotion();
        systemMessage.setMessageId("2");
        systemMessageProvider.sendSysMessage(systemMessage);
    }
}
